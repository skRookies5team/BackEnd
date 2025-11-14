package com.AIagnet.agent.common.service;

import com.AIagnet.agent.common.dto.response.EmployeeResponse;
import com.AIagnet.agent.common.entity.Employee;
import com.AIagnet.agent.common.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /** 전체 직원 조회 */
    public List<EmployeeResponse> getAllEmployees() {
        log.info("전체 직원 조회");
        return employeeRepository.findAll().stream()
                .map(EmployeeResponse::from)
                .collect(Collectors.toList());
    }

    /** 직원 상세 조회 */
    public EmployeeResponse getEmployeeById(Integer id) {
        log.info("직원 상세 조회: id={}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다. id=" + id));
        return EmployeeResponse.from(employee);
    }

    /** 이메일로 직원 조회 */
    public EmployeeResponse getEmployeeByEmail(String email) {
        log.info("이메일로 직원 조회: email={}", email);
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다. email=" + email));
        return EmployeeResponse.from(employee);
    }

    /** 이름으로 검색 */
    public List<EmployeeResponse> searchEmployeesByName(String name) {
        log.info("이름으로 직원 검색: name={}", name);
        return employeeRepository.findByNameContaining(name).stream()
                .map(EmployeeResponse::from)
                .collect(Collectors.toList());
    }

    /** 부서별 조회 (DB 컬럼: dept) */
    public List<EmployeeResponse> getEmployeesByDept(String dept) {
        log.info("부서별 직원 조회: dept={}", dept);
        return employeeRepository.findByDept(dept).stream()
                .map(EmployeeResponse::from)
                .collect(Collectors.toList());
    }
}