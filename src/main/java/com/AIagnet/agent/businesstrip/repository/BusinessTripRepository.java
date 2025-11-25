package com.AIagnet.agent.businesstrip.repository;

import com.AIagnet.agent.businesstrip.entity.BusinessTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BusinessTripRepository extends JpaRepository<BusinessTrip, Integer> {

    @Query("""
            SELECT bt
            FROM BusinessTrip bt
            JOIN FETCH bt.employee
            WHERE bt.employee.employeeId = :employeeId
            ORDER BY bt.startTime DESC
            """)
    List<BusinessTrip> findByEmployeeEmployeeIdOrderByStartTimeDesc(@Param("employeeId") Integer employeeId);

    @Query("""
            SELECT bt
            FROM BusinessTrip bt
            JOIN FETCH bt.employee
            WHERE bt.employee.employeeId = :employeeId
              AND (:startTime IS NULL OR bt.endTime >= :startTime)
              AND (:endTime IS NULL OR bt.startTime <= :endTime)
            ORDER BY bt.startTime DESC
            """)
    List<BusinessTrip> findByEmployeeAndTimeRange(
            @Param("employeeId") Integer employeeId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("""
            SELECT bt
            FROM BusinessTrip bt
            JOIN FETCH bt.employee
            ORDER BY bt.startTime DESC
            """)
    List<BusinessTrip> findAllWithEmployee();

    @Query("""
            SELECT bt
            FROM BusinessTrip bt
            JOIN FETCH bt.employee
            WHERE bt.tripId = :tripId
            """)
    Optional<BusinessTrip> findByIdWithEmployee(@Param("tripId") Integer tripId);
}


