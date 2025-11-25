package com.AIagnet.agent.sales.dto.response;

import com.AIagnet.agent.sales.entity.CompanySales;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회사 매출 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanySalesResponse {
    private Long id;
    private Integer orderId;
    private LocalDate orderDate;
    private BigDecimal unitCost;
    private BigDecimal unitPrice;
    private Integer orderQuantity;
    private BigDecimal costOfSales;
    private BigDecimal revenue;
    private BigDecimal profit;
    private String salesChannel;
    private String promotionName;
    private String productName;
    private String manufacturer;
    private String productSubcategory;
    private String productCategory;
    private String region;
    private String city;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CompanySalesResponse from(CompanySales sales) {
        return CompanySalesResponse.builder()
                .id(sales.getId())
                .orderId(sales.getOrderId())
                .orderDate(sales.getOrderDate())
                .unitCost(sales.getUnitCost())
                .unitPrice(sales.getUnitPrice())
                .orderQuantity(sales.getOrderQuantity())
                .costOfSales(sales.getCostOfSales())
                .revenue(sales.getRevenue())
                .profit(sales.getProfit())
                .salesChannel(sales.getSalesChannel())
                .promotionName(sales.getPromotionName())
                .productName(sales.getProductName())
                .manufacturer(sales.getManufacturer())
                .productSubcategory(sales.getProductSubcategory())
                .productCategory(sales.getProductCategory())
                .region(sales.getRegion())
                .city(sales.getCity())
                .country(sales.getCountry())
                .createdAt(sales.getCreatedAt())
                .updatedAt(sales.getUpdatedAt())
                .build();
    }
}

