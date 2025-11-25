package com.AIagnet.agent.recruitment.repository;

import com.AIagnet.agent.recruitment.entity.RecruitmentApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 채용 지원서 리포지토리.
 */
public interface RecruitmentApplicationRepository extends JpaRepository<RecruitmentApplication, Long> {

    /**
     * 지원 상태로 조회.
     */
    List<RecruitmentApplication> findByApplicationStatus(String status);

    /**
     * 지원 직무로 조회.
     */
    List<RecruitmentApplication> findByAppliedPosition(String position);

    /**
     * 지원일자 범위로 조회.
     */
    List<RecruitmentApplication> findByApplicationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 검색.
     */
    @Query("""
            SELECT r
            FROM RecruitmentApplication r
            WHERE (:startDate IS NULL OR r.applicationDate >= :startDate)
              AND (:endDate IS NULL OR r.applicationDate <= :endDate)
              AND (:appliedPosition IS NULL OR r.appliedPosition = :appliedPosition)
              AND (:applicationStatus IS NULL OR r.applicationStatus = :applicationStatus)
            ORDER BY r.applicationDate DESC
            """)
    Page<RecruitmentApplication> search(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("appliedPosition") String appliedPosition,
            @Param("applicationStatus") String applicationStatus,
            Pageable pageable
    );
}

