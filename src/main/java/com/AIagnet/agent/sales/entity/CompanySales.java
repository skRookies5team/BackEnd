package com.AIagnet.agent.sales.entity;

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
 * 회사 매출 엔터티.
 */
@Entity
@Table(name = "company_sales")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanySales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "unit_cost", precision = 15, scale = 5)
    private BigDecimal unitCost;

    @Column(name = "unit_price", precision = 15, scale = 5)
    private BigDecimal unitPrice;

    @Column(name = "order_quantity")
    private Integer orderQuantity;

    @Column(name = "cost_of_sales", precision = 15, scale = 5)
    private BigDecimal costOfSales;

    @Column(name = "revenue", precision = 15, scale = 5)
    private BigDecimal revenue;

    @Column(name = "profit", precision = 15, scale = 5)
    private BigDecimal profit;

    @Column(name = "sales_channel", length = 200)
    private String salesChannel;

    @Column(name = "promotion_name", length = 300)
    private String promotionName;

    @Column(name = "product_name", length = 500)
    private String productName;

    @Column(name = "manufacturer", length = 300)
    private String manufacturer;

    @Column(name = "product_subcategory", length = 300)
    private String productSubcategory;

    @Column(name = "product_category", length = 300)
    private String productCategory;

    @Column(name = "region", length = 200)
    private String region;

    @Column(name = "city", length = 200)
    private String city;

    @Column(name = "country", length = 200)
    private String country;

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

