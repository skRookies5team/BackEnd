package com.AIagnet.agent.leave.service;

import com.AIagnet.agent.common.entity.Employee;
import com.AIagnet.agent.common.repository.EmployeeRepository;
import com.AIagnet.agent.exception.BusinessException;
import com.AIagnet.agent.exception.ErrorCode;
import com.AIagnet.agent.leave.dto.request.LeaveRequestCreateRequest;
import com.AIagnet.agent.leave.dto.request.LeaveRequestSearchRequest;
import com.AIagnet.agent.leave.dto.request.LeaveRequestUpdateRequest;
import com.AIagnet.agent.leave.dto.response.LeaveRequestResponse;
import com.AIagnet.agent.leave.entity.LeaveRequest;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveRequestStatus;
import com.AIagnet.agent.leave.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 연차 신청 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * 연차 신청 목록 조회.
     */
    public List<LeaveRequestResponse> searchLeaveRequests(LeaveRequestSearchRequest request) {
        List<LeaveRequest> leaveRequests;

        if (request.getEmployeeId() != null) {
            if (request.getStatus() != null) {
                leaveRequests = leaveRequestRepository.findByEmployeeEmployeeIdAndStatusOrderByRequestDateDesc(
                        request.getEmployeeId(), request.getStatus());
            } else {
                leaveRequests = leaveRequestRepository.findByEmployeeAndDateRange(
                        request.getEmployeeId(),
                        request.getStartDate(),
                        request.getEndDate());
            }
        } else if (request.getStatus() != null) {
            leaveRequests = leaveRequestRepository.findByStatusAndDateRange(
                    request.getStatus(),
                    request.getStartDate(),
                    request.getEndDate());
        } else if (request.getLeaveType() != null) {
            leaveRequests = leaveRequestRepository.findByLeaveTypeAndDateRange(
                    request.getLeaveType(),
                    request.getStartDate(),
                    request.getEndDate());
        } else {
            leaveRequests = leaveRequestRepository.findAllWithEmployee();
        }

        log.info("연차 신청 조회: 조회된 건수={}", leaveRequests.size());
        return leaveRequests.stream()
                .map(LeaveRequestResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 연차 신청 상세 조회.
     */
    public LeaveRequestResponse getLeaveRequestById(Integer id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LEAVE_REQUEST_NOT_FOUND,
                        "연차 신청을 찾을 수 없습니다. id=" + id
                ));
        return LeaveRequestResponse.from(leaveRequest);
    }

    /**
     * 연차 신청 생성.
     */
    @Transactional
    public LeaveRequestResponse createLeaveRequest(LeaveRequestCreateRequest request) {
        validateDateRange(request.getStartDate(), request.getEndDate());

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.EMPLOYEE_NOT_FOUND,
                        "직원을 찾을 수 없습니다. employeeId=" + request.getEmployeeId()
                ));

        String requestId = generateRequestId();

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .requestId(requestId)
                .employee(employee)
                .requestDate(request.getRequestDate() != null ? request.getRequestDate() : LocalDate.now())
                .leaveType(request.getLeaveType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .totalDays(request.getTotalDays())
                .reason(request.getReason())
                .status(LeaveRequestStatus.대기)
                .build();

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        log.info("연차 신청 생성 완료: id={}, requestId={}", saved.getId(), saved.getRequestId());
        return LeaveRequestResponse.from(saved);
    }

    /**
     * 연차 신청 수정.
     */
    @Transactional
    public LeaveRequestResponse updateLeaveRequest(Integer id, LeaveRequestUpdateRequest request) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LEAVE_REQUEST_NOT_FOUND,
                        "연차 신청을 찾을 수 없습니다. id=" + id
                ));

        if (leaveRequest.getStatus() == LeaveRequestStatus.승인) {
            throw new BusinessException(
                    ErrorCode.LEAVE_REQUEST_ALREADY_APPROVED,
                    "이미 승인된 연차 신청은 수정할 수 없습니다."
            );
        }

        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : leaveRequest.getStartDate();
        LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : leaveRequest.getEndDate();

        if (request.getStartDate() != null || request.getEndDate() != null) {
            validateDateRange(startDate, endDate);
        }

        if (request.getLeaveType() != null) {
            leaveRequest.setLeaveType(request.getLeaveType());
        }
        if (request.getStartDate() != null) {
            leaveRequest.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            leaveRequest.setEndDate(request.getEndDate());
        }
        if (request.getTotalDays() != null) {
            leaveRequest.setTotalDays(request.getTotalDays());
        }
        if (request.getStatus() != null) {
            leaveRequest.setStatus(request.getStatus());
        }
        if (request.getReason() != null) {
            leaveRequest.setReason(request.getReason());
        }

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        log.info("연차 신청 수정 완료: id={}", saved.getId());
        return LeaveRequestResponse.from(saved);
    }

    /**
     * 연차 신청 삭제.
     */
    @Transactional
    public void deleteLeaveRequest(Integer id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LEAVE_REQUEST_NOT_FOUND,
                        "연차 신청을 찾을 수 없습니다. id=" + id
                ));

        if (leaveRequest.getStatus() == LeaveRequestStatus.승인) {
            throw new BusinessException(
                    ErrorCode.LEAVE_REQUEST_ALREADY_APPROVED,
                    "이미 승인된 연차 신청은 삭제할 수 없습니다."
            );
        }

        leaveRequestRepository.deleteById(id);
        log.info("연차 신청 삭제 완료: id={}", id);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || !startDate.isBefore(endDate) && !startDate.isEqual(endDate)) {
            throw new BusinessException(
                    ErrorCode.INVALID_INPUT_VALUE,
                    "시작일은 종료일보다 같거나 빨라야 합니다."
            );
        }
    }

    private String generateRequestId() {
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "LR-" + datePrefix + "-" + uuid;
    }
}

