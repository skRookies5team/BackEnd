package com.AIagnet.agent.leave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 연차 잔여일 엔터티.
 */
@Entity
@Table(name = "leave_balance_v2")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LeaveBalance {

    @EmbeddedId
    private LeaveBalanceId id;

    @Column(name = "total_entitlement_days")
    private Integer totalEntitlementDays;

    @Column(name = "carried_over_days")
    private Integer carriedOverDays;

    @Column(name = "used_days")
    private Integer usedDays;

    @Column(name = "pending_days")
    private Integer pendingDays;

    @Column(name = "remaining_days")
    private Integer remainingDays;

    public Integer getEmployeeId() {
        return id != null ? id.getEmployeeId() : null;
    }

    public Integer getYear() {
        return id != null ? id.getYear() : null;
    }
}

