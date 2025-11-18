package com.AIagnet.agent.leave.dto;

import com.AIagnet.agent.leave.entity.LeaveBalance;
import lombok.Builder;
import lombok.Getter;

/**
 * 연차 잔여일 응답.
 */
@Getter
@Builder
public class LeaveBalanceResponse {

    private final Integer employeeId;
    private final Integer year;
    private final Integer totalEntitlementDays;
    private final Integer carriedOverDays;
    private final Integer usedDays;
    private final Integer pendingDays;
    private final Integer remainingDays;

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

