package com.AIagnet.agent.mobiledevice.repository;

import com.AIagnet.agent.mobiledevice.entity.MobileDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 모바일 기기 리포지토리.
 */
public interface MobileDeviceRepository extends JpaRepository<MobileDevice, Integer> {

    /**
     * 회사명으로 조회.
     */
    List<MobileDevice> findByCompanyName(String companyName);

    /**
     * 모델명으로 조회.
     */
    List<MobileDevice> findByModelName(String modelName);

    /**
     * 출시년도로 조회.
     */
    List<MobileDevice> findByReleaseYear(Integer releaseYear);

    /**
     * 검색.
     */
    @Query("""
            SELECT m
            FROM MobileDevice m
            WHERE (:companyName IS NULL OR m.companyName = :companyName)
              AND (:modelName IS NULL OR m.modelName LIKE %:modelName%)
              AND (:releaseYear IS NULL OR m.releaseYear = :releaseYear)
            ORDER BY m.releaseYear DESC, m.modelName ASC
            """)
    Page<MobileDevice> search(
            @Param("companyName") String companyName,
            @Param("modelName") String modelName,
            @Param("releaseYear") Integer releaseYear,
            Pageable pageable
    );
}

