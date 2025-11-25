package com.AIagnet.agent.spend.repository;

import com.AIagnet.agent.spend.entity.SpendTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 지출 거래 리포지토리.
 */
public interface SpendTransactionRepository extends JpaRepository<SpendTransaction, Long> {

    /**
     * 거래 ID로 조회.
     */
    Optional<SpendTransaction> findByTransactionId(String transactionId);

    /**
     * 카테고리로 조회.
     */
    List<SpendTransaction> findByCategory(String category);

    /**
     * 구매일자 범위로 조회.
     */
    List<SpendTransaction> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 공급업체로 조회.
     */
    List<SpendTransaction> findBySupplier(String supplier);

    /**
     * 구매자로 조회.
     */
    List<SpendTransaction> findByBuyer(String buyer);

    /**
     * 검색.
     */
    @Query("""
            SELECT s
            FROM SpendTransaction s
            WHERE (:startDate IS NULL OR s.purchaseDate >= :startDate)
              AND (:endDate IS NULL OR s.purchaseDate <= :endDate)
              AND (:category IS NULL OR s.category = :category)
              AND (:supplier IS NULL OR s.supplier = :supplier)
              AND (:buyer IS NULL OR s.buyer = :buyer)
            ORDER BY s.purchaseDate DESC
            """)
    Page<SpendTransaction> search(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("category") String category,
            @Param("supplier") String supplier,
            @Param("buyer") String buyer,
            Pageable pageable
    );
}

