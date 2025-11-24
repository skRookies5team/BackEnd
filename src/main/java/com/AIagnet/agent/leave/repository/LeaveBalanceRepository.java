package com.AIagnet.agent.leave.repository;

import com.AIagnet.agent.leave.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

    Optional<LeaveBalance> findByEmployeeIdAndYear(Integer employeeId, Integer year);

    Optional<LeaveBalance> findFirstByEmployeeIdOrderByYearDesc(Integer employeeId);

    List<LeaveBalance> findByEmployeeIdOrderByYearDesc(Integer employeeId);

    List<LeaveBalance> findByYearOrderByEmployeeIdAsc(Integer year);
}

