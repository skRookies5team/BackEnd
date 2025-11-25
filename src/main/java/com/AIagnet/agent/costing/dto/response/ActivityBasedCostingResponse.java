package com.AIagnet.agent.costing.dto.response;

import com.AIagnet.agent.costing.entity.ActivityBasedCosting;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 활동기준원가 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityBasedCostingResponse {
    private Long id;
    private String activityName;
    private String costDriver;
    private BigDecimal costRatePerUnit;
    private Integer activityQuantity;
    private BigDecimal totalActivityCost;
    private BigDecimal fixedCost;
    private BigDecimal variableCost;
    private String fixedVariableRatio;
    private String activityType;
    private String department;
    private BigDecimal potentialSavingsCost;
    private BigDecimal nonEssentialActivityCost;
    private String productivityImpactLevel;
    private String costPool;
    private LocalDate dataReferenceDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ActivityBasedCostingResponse from(ActivityBasedCosting costing) {
        return ActivityBasedCostingResponse.builder()
                .id(costing.getId())
                .activityName(costing.getActivityName())
                .costDriver(costing.getCostDriver())
                .costRatePerUnit(costing.getCostRatePerUnit())
                .activityQuantity(costing.getActivityQuantity())
                .totalActivityCost(costing.getTotalActivityCost())
                .fixedCost(costing.getFixedCost())
                .variableCost(costing.getVariableCost())
                .fixedVariableRatio(costing.getFixedVariableRatio())
                .activityType(costing.getActivityType())
                .department(costing.getDepartment())
                .potentialSavingsCost(costing.getPotentialSavingsCost())
                .nonEssentialActivityCost(costing.getNonEssentialActivityCost())
                .productivityImpactLevel(costing.getProductivityImpactLevel())
                .costPool(costing.getCostPool())
                .dataReferenceDate(costing.getDataReferenceDate())
                .createdAt(costing.getCreatedAt())
                .updatedAt(costing.getUpdatedAt())
                .build();
    }
}

