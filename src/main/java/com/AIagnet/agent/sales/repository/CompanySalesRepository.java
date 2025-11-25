package com.AIagnet.agent.sales.repository;

import com.AIagnet.agent.sales.entity.CompanySales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 회사 매출 리포지토리.
 */
public interface CompanySalesRepository extends JpaRepository<CompanySales, Long> {

    /**
     * 주문일자 범위로 조회.
     */
    List<CompanySales> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 제품 카테고리로 조회.
     */
    List<CompanySales> findByProductCategory(String productCategory);

    /**
     * 지역으로 조회.
     */
    List<CompanySales> findByRegion(String region);

    /**
     * 국가로 조회.
     */
    List<CompanySales> findByCountry(String country);

    /**
     * 주문일자 범위와 제품 카테고리로 조회.
     */
    @Query("""
            SELECT cs
            FROM CompanySales cs
            WHERE (:startDate IS NULL OR cs.orderDate >= :startDate)
              AND (:endDate IS NULL OR cs.orderDate <= :endDate)
              AND (:productCategory IS NULL OR cs.productCategory = :productCategory)
              AND (:region IS NULL OR cs.region = :region)
              AND (:country IS NULL OR cs.country = :country)
            ORDER BY cs.orderDate DESC
            """)
    Page<CompanySales> search(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("productCategory") String productCategory,
            @Param("region") String region,
            @Param("country") String country,
            Pageable pageable
    );

    /**
     * 기간별 매출 합계.
     */
    @Query("""
            SELECT SUM(cs.revenue) as totalRevenue,
                   SUM(cs.profit) as totalProfit,
                   SUM(cs.costOfSales) as totalCost
            FROM CompanySales cs
            WHERE (:startDate IS NULL OR cs.orderDate >= :startDate)
              AND (:endDate IS NULL OR cs.orderDate <= :endDate)
            """)
    Object[] getSalesSummary(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

