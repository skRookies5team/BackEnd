package com.AIagnet.agent.leave.dto;

import com.AIagnet.agent.leave.entity.LeaveBalance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 연차 잔여일 응답.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LeaveBalanceResponse {

    private Integer employeeId;
    private Integer year;
    private BigDecimal totalEntitlementDays;
    private BigDecimal carriedOverDays;
    private BigDecimal usedDays;
    private BigDecimal pendingDays;
    private BigDecimal remainingDays;

    public static LeaveBalanceResponse from(LeaveBalance entity) {
        return LeaveBalanceResponse.builder()
                .employeeId(entity.getEmployeeId())
                .year(entity.getYear())
                .totalEntitlementDays(entity.getTotalEntitlementDays())
                .carriedOverDays(entity.getCarriedOverDays())
                .usedDays(entity.getUsedDays())
                .pendingDays(entity.getPendingDays())
                .remainingDays(entity.getRemainingDays())
                .build();
    }
}

