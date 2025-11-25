package com.AIagnet.agent.businesstrip.service;

import com.AIagnet.agent.businesstrip.dto.request.BusinessTripCreateRequest;
import com.AIagnet.agent.businesstrip.dto.request.BusinessTripSearchRequest;
import com.AIagnet.agent.businesstrip.dto.response.BusinessTripResponse;
import com.AIagnet.agent.businesstrip.entity.BusinessTrip;
import com.AIagnet.agent.businesstrip.repository.BusinessTripRepository;
import com.AIagnet.agent.common.entity.Employee;
import com.AIagnet.agent.common.repository.EmployeeRepository;
import com.AIagnet.agent.exception.BusinessException;
import com.AIagnet.agent.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 출장 관리 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BusinessTripService {

    private final BusinessTripRepository businessTripRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * 출장 목록 조회.
     */
    public List<BusinessTripResponse> getBusinessTrips(BusinessTripSearchRequest request) {
        List<BusinessTrip> trips;

        if (request.getEmployeeId() != null && request.getStartTime() != null && request.getEndTime() != null) {
            trips = businessTripRepository.findByEmployeeAndTimeRange(
                    request.getEmployeeId(),
                    request.getStartTime(),
                    request.getEndTime());
        } else if (request.getEmployeeId() != null) {
            trips = businessTripRepository.findByEmployeeEmployeeIdOrderByStartTimeDesc(request.getEmployeeId());
        } else {
            trips = businessTripRepository.findAllWithEmployee();
        }

        log.info("출장 목록 조회: 조회된 건수={}", trips.size());
        return trips.stream()
                .map(BusinessTripResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 출장 상세 조회.
     */
    public BusinessTripResponse getBusinessTripById(Integer tripId) {
        BusinessTrip trip = businessTripRepository.findByIdWithEmployee(tripId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "출장을 찾을 수 없습니다. tripId=" + tripId
                ));
        return BusinessTripResponse.from(trip);
    }

    /**
     * 출장 등록.
     */
    @Transactional
    public BusinessTripResponse createBusinessTrip(BusinessTripCreateRequest request) {
        validateTimeRange(request.getStartTime(), request.getEndTime());

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.EMPLOYEE_NOT_FOUND,
                        "직원을 찾을 수 없습니다. employeeId=" + request.getEmployeeId()
                ));

        BusinessTrip trip = BusinessTrip.builder()
                .employee(employee)
                .tripTitle(request.getTripTitle())
                .tripPurpose(request.getTripPurpose())
                .destination(request.getDestination())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .alertTime(request.getAlertTime())
                .build();

        BusinessTrip saved = businessTripRepository.save(trip);
        log.info("출장 등록 완료: tripId={}", saved.getTripId());
        return BusinessTripResponse.from(saved);
    }

    /**
     * 출장 삭제.
     */
    @Transactional
    public void deleteBusinessTrip(Integer tripId) {
        if (!businessTripRepository.existsById(tripId)) {
            throw new BusinessException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "출장을 찾을 수 없습니다. tripId=" + tripId
            );
        }
        businessTripRepository.deleteById(tripId);
        log.info("출장 삭제 완료: tripId={}", tripId);
    }

    private void validateTimeRange(java.time.LocalDateTime startTime, java.time.LocalDateTime endTime) {
        if (startTime == null || endTime == null || !startTime.isBefore(endTime)) {
            throw new BusinessException(
                    ErrorCode.INVALID_INPUT_VALUE,
                    "시작 시간은 종료 시간보다 빨라야 합니다."
            );
        }
    }
}


