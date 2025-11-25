package com.AIagnet.agent.common.controller;

import com.AIagnet.agent.common.dto.response.EmployeeResponse;
import com.AIagnet.agent.common.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 직원 컨트롤러
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "직원 관리", description = "직원 정보 조회 API")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 전체 직원 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        log.info("GET /api/employees - 전체 직원 목록 조회");
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * 직원 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Integer id) {
        log.info("GET /api/employees/{} - 직원 상세 조회", id);
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * 이메일로 직원 조회
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponse> getEmployeeByEmail(@PathVariable String email) {
        log.info("GET /api/employees/email/{} - 이메일로 직원 조회", email);
        EmployeeResponse employee = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(employee);
    }

    /**
     * 부서별 직원 목록 조회
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartment(@PathVariable String department) {
        log.info("GET /api/employees/department/{} - 부서별 직원 조회", department);
        List<EmployeeResponse> employees = employeeService.getEmployeesByDept(department);
        return ResponseEntity.ok(employees);
    }

    /**
     * 이름으로 직원 검색
     */
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchEmployeesByName(@RequestParam String name) {
        log.info("GET /api/employees/search?name={} - 이름으로 직원 검색", name);
        List<EmployeeResponse> employees = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(employees);
    }
}