package com.AIagnet.agent.leave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 연차 잔여일 엔터티.
 */
@Entity
@Table(name = "leave_balance")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "total_entitlement_days", precision = 5, scale = 2)
    private BigDecimal totalEntitlementDays;

    @Column(name = "carried_over_days", precision = 5, scale = 2)
    private BigDecimal carriedOverDays;

    @Column(name = "used_days", precision = 5, scale = 2)
    private BigDecimal usedDays;

    @Column(name = "pending_days", precision = 5, scale = 2)
    private BigDecimal pendingDays;

    @Column(name = "remaining_days", precision = 5, scale = 2)
    private BigDecimal remainingDays;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

