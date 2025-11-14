package com.AIagnet.agent.common.repository;

import com.AIagnet.agent.common.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 직원 Repository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * 이름으로 직원 조회
     */
    Optional<Employee> findByName(String name);

    /**
     * 이메일로 직원 조회
     */
    Optional<Employee> findByEmail(String email);

    /**
     * 부서별 직원 목록 조회
     */
    List<Employee> findByDept(String dept);

    /**
     * 이름으로 직원 검색 (LIKE 검색)
     */
    List<Employee> findByNameContaining(String name);

    /**
     * 이메일 존재 여부 확인
     */
    boolean existsByEmail(String email);
}