package com.AIagnet.agent.costing.repository;

import com.AIagnet.agent.costing.entity.ActivityBasedCosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 활동기준원가 리포지토리.
 */
public interface ActivityBasedCostingRepository extends JpaRepository<ActivityBasedCosting, Long> {

    /**
     * 활동 유형으로 조회.
     */
    List<ActivityBasedCosting> findByActivityType(String activityType);

    /**
     * 부서로 조회.
     */
    List<ActivityBasedCosting> findByDepartment(String department);

    /**
     * 생산성 영향 수준으로 조회.
     */
    List<ActivityBasedCosting> findByProductivityImpactLevel(String level);

    /**
     * 참조일자로 조회.
     */
    List<ActivityBasedCosting> findByDataReferenceDate(LocalDate date);

    /**
     * 검색.
     */
    @Query("""
            SELECT a
            FROM ActivityBasedCosting a
            WHERE (:activityType IS NULL OR a.activityType = :activityType)
              AND (:department IS NULL OR a.department = :department)
              AND (:productivityImpactLevel IS NULL OR a.productivityImpactLevel = :productivityImpactLevel)
              AND (:startDate IS NULL OR a.dataReferenceDate >= :startDate)
              AND (:endDate IS NULL OR a.dataReferenceDate <= :endDate)
            ORDER BY a.dataReferenceDate DESC, a.activityName ASC
            """)
    Page<ActivityBasedCosting> search(
            @Param("activityType") String activityType,
            @Param("department") String department,
            @Param("productivityImpactLevel") String productivityImpactLevel,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}

