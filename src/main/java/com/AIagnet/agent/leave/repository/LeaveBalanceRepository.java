package com.AIagnet.agent.leave.repository;

import com.AIagnet.agent.leave.entity.LeaveBalance;
import com.AIagnet.agent.leave.entity.LeaveBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, LeaveBalanceId> {

    Optional<LeaveBalance> findByIdEmployeeIdAndIdYear(Integer employeeId, Integer year);

    Optional<LeaveBalance> findFirstByIdEmployeeIdOrderByIdYearDesc(Integer employeeId);

    List<LeaveBalance> findByIdEmployeeIdOrderByIdYearDesc(Integer employeeId);

    List<LeaveBalance> findByIdYearOrderByIdEmployeeIdAsc(Integer year);
}

