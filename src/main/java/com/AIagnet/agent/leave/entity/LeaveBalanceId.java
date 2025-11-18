package com.AIagnet.agent.leave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 연차 잔여일 복합키 (직원, 연도).
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LeaveBalanceId implements Serializable {

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "year", nullable = false)
    private Integer year;
}

