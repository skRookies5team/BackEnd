package com.AIagnet.agent.costing.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 활동기준원가 엔터티.
 */
@Entity
@Table(name = "activity_based_costing")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityBasedCosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activity_name", length = 500)
    private String activityName;

    @Column(name = "cost_driver", length = 300)
    private String costDriver;

    @Column(name = "cost_rate_per_unit", precision = 15, scale = 5)
    private BigDecimal costRatePerUnit;

    @Column(name = "activity_quantity")
    private Integer activityQuantity;

    @Column(name = "total_activity_cost", precision = 15, scale = 5)
    private BigDecimal totalActivityCost;

    @Column(name = "fixed_cost", precision = 15, scale = 5)
    private BigDecimal fixedCost;

    @Column(name = "variable_cost", precision = 15, scale = 5)
    private BigDecimal variableCost;

    @Column(name = "fixed_variable_ratio", length = 200)
    private String fixedVariableRatio;

    @Column(name = "activity_type", length = 200)
    private String activityType;

    @Column(name = "department", length = 200)
    private String department;

    @Column(name = "potential_savings_cost", precision = 15, scale = 5)
    private BigDecimal potentialSavingsCost;

    @Column(name = "non_essential_activity_cost", precision = 15, scale = 5)
    private BigDecimal nonEssentialActivityCost;

    @Column(name = "productivity_impact_level", length = 50)
    private String productivityImpactLevel;

    @Column(name = "cost_pool", length = 300)
    private String costPool;

    @Column(name = "data_reference_date")
    private LocalDate dataReferenceDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

