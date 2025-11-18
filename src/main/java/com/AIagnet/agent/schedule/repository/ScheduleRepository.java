package com.AIagnet.agent.schedule.repository;

import com.AIagnet.agent.schedule.entity.Schedule;
import com.AIagnet.agent.schedule.entity.ScheduleStatus;
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
                          @Param("status") ScheduleStatus status);

    @Query("""
            SELECT s
            FROM Schedule s
            WHERE s.employee.employeeId = :employeeId
              AND s.status = :status
              AND (:startTime IS NULL OR s.endTime >= :startTime)
              AND (:endTime IS NULL OR s.startTime <= :endTime)
            ORDER BY s.startTime ASC
            """)
    List<Schedule> findByEmployeeAndRange(@Param("employeeId") Integer employeeId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime,
                                          @Param("status") ScheduleStatus status);

    List<Schedule> findByEmployeeEmployeeIdAndStatusOrderByStartTimeAsc(Integer employeeId, ScheduleStatus status);

    @Query("""
            SELECT s
            FROM Schedule s
            WHERE s.status = :status
              AND (:startTime IS NULL OR s.endTime >= :startTime)
              AND (:endTime IS NULL OR s.startTime <= :endTime)
            ORDER BY s.startTime ASC
            """)
    List<Schedule> findByStatusAndRange(@Param("status") ScheduleStatus status,
                                        @Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime);
}


