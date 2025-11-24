package com.AIagnet.agent.leave.repository;

import com.AIagnet.agent.leave.entity.LeaveRequest;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveRequestStatus;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 연차 신청 Repository.
 */
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    Optional<LeaveRequest> findByRequestId(String requestId);

    List<LeaveRequest> findByEmployeeEmployeeIdOrderByRequestDateDesc(Integer employeeId);

    List<LeaveRequest> findByEmployeeEmployeeIdAndStatusOrderByRequestDateDesc(
            Integer employeeId, LeaveRequestStatus status);

    @Query("""
            SELECT lr
            FROM LeaveRequest lr
            JOIN FETCH lr.employee
            WHERE lr.employee.employeeId = :employeeId
              AND (:startDate IS NULL OR lr.endDate >= :startDate)
              AND (:endDate IS NULL OR lr.startDate <= :endDate)
            ORDER BY lr.requestDate DESC
            """)
    List<LeaveRequest> findByEmployeeAndDateRange(
            @Param("employeeId") Integer employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT lr
            FROM LeaveRequest lr
            JOIN FETCH lr.employee
            WHERE lr.status = :status
              AND (:startDate IS NULL OR lr.endDate >= :startDate)
              AND (:endDate IS NULL OR lr.startDate <= :endDate)
            ORDER BY lr.requestDate DESC
            """)
    List<LeaveRequest> findByStatusAndDateRange(
            @Param("status") LeaveRequestStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT lr
            FROM LeaveRequest lr
            JOIN FETCH lr.employee
            WHERE lr.leaveType = :leaveType
              AND (:startDate IS NULL OR lr.endDate >= :startDate)
              AND (:endDate IS NULL OR lr.startDate <= :endDate)
            ORDER BY lr.requestDate DESC
            """)
    List<LeaveRequest> findByLeaveTypeAndDateRange(
            @Param("leaveType") LeaveType leaveType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT lr
            FROM LeaveRequest lr
            JOIN FETCH lr.employee
            ORDER BY lr.requestDate DESC
            """)
    List<LeaveRequest> findAllWithEmployee();
}

