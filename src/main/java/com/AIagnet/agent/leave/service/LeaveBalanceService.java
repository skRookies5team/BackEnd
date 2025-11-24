package com.AIagnet.agent.leave.service;

import com.AIagnet.agent.exception.BusinessException;
import com.AIagnet.agent.exception.ErrorCode;
import com.AIagnet.agent.leave.dto.LeaveBalanceResponse;
import com.AIagnet.agent.leave.entity.LeaveBalance;
import com.AIagnet.agent.leave.repository.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;

    /**
     * 연차 잔여일 조회.
     *
     * @param employeeId 직원 ID
     * @param year       조회 연도 (null 이면 최신 연도)
     * @return 잔여일 응답
     */
    public LeaveBalanceResponse getLeaveBalance(Integer employeeId, Integer year) {
        LeaveBalance balance = (year != null)
                ? leaveBalanceRepository.findByEmployeeIdAndYear(employeeId, year)
                .orElseThrow(() -> new BusinessException(ErrorCode.LEAVE_BALANCE_NOT_FOUND, "해당 연도의 연차 데이터를 찾을 수 없습니다."))
                : leaveBalanceRepository.findFirstByEmployeeIdOrderByYearDesc(employeeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LEAVE_BALANCE_NOT_FOUND, "연차 데이터가 존재하지 않습니다."));

        log.info("연차 잔여일 조회: employeeId={}, year={}", balance.getEmployeeId(), balance.getYear());
        return LeaveBalanceResponse.from(balance);
    }

    /**
     * 연차 잔여일 목록 조회.
     */
    public List<LeaveBalanceResponse> listLeaveBalances(Integer employeeId, Integer year) {
        List<LeaveBalance> balances;

        if (employeeId != null && year != null) {
            balances = leaveBalanceRepository.findByEmployeeIdAndYear(employeeId, year)
                    .map(List::of)
                    .orElse(List.of());
        } else if (employeeId != null) {
            balances = leaveBalanceRepository.findByEmployeeIdOrderByYearDesc(employeeId);
        } else if (year != null) {
            balances = leaveBalanceRepository.findByYearOrderByEmployeeIdAsc(year);
        } else {
            balances = leaveBalanceRepository.findAll();
        }

        return balances.stream()
                .map(LeaveBalanceResponse::from)
                .toList();
    }
}

