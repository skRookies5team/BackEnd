package com.AIagnet.agent.schedule.repository;

import com.AIagnet.agent.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("""
            SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
            FROM Schedule s
            WHERE s.employee.employeeId = :employeeId
              AND s.status = :status
              AND s.endTime > :startTime
              AND s.startTime < :endTime
            """)
    boolean existsOverlap(@Param("employeeId") Integer employeeId,
                          @Param("startTime") LocalDateTime startTime,
                          @Param("endTime") LocalDateTime endTime,
                          @Param("status") String status);

    @Query("""
            SELECT s
            FROM Schedule s
            JOIN FETCH s.employee
            WHERE s.employee.employeeId = :employeeId
              AND s.status = :status
              AND (:startTime IS NULL OR s.endTime >= :startTime)
              AND (:endTime IS NULL OR s.startTime <= :endTime)
            ORDER BY s.startTime ASC
            """)
    List<Schedule> findByEmployeeAndRange(@Param("employeeId") Integer employeeId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime,
                                          @Param("status") String status);

    @Query("""
            SELECT s
            FROM Schedule s
            JOIN FETCH s.employee
            WHERE s.employee.employeeId = :employeeId
              AND s.status = :status
            ORDER BY s.startTime ASC
            """)
    List<Schedule> findByEmployeeEmployeeIdAndStatusOrderByStartTimeAsc(@Param("employeeId") Integer employeeId,
                                                                          @Param("status") String status);

    @Query("""
            SELECT s
            FROM Schedule s
            JOIN FETCH s.employee
            WHERE s.status = :status
              AND (:startTime IS NULL OR s.endTime >= :startTime)
              AND (:endTime IS NULL OR s.startTime <= :endTime)
            ORDER BY s.startTime ASC
            """)
    List<Schedule> findByStatusAndRange(@Param("status") String status,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);

    @Query("""
            SELECT s
            FROM Schedule s
            JOIN FETCH s.employee
            WHERE s.scheduleId = :scheduleId
            """)
    java.util.Optional<Schedule> findByIdWithEmployee(@Param("scheduleId") Integer scheduleId);
}


