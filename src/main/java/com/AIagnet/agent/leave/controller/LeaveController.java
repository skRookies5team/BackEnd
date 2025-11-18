package com.AIagnet.agent.leave.controller;

import com.AIagnet.agent.leave.dto.LeaveBalanceResponse;
import com.AIagnet.agent.leave.service.LeaveBalanceService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 연차 잔여일 조회 API.
 */
@RestController
@RequestMapping("/api/leave")
@RequiredArgsConstructor
@Validated
@Slf4j
public class LeaveController {

    private final LeaveBalanceService leaveBalanceService;

    /**
     * 연차 잔여일 목록 조회.
     */
    @GetMapping
    public ResponseEntity<List<LeaveBalanceResponse>> listLeaveBalances(
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) Integer year
    ) {
        log.info("GET /api/leave - employeeId={}, year={}", employeeId, year);
        return ResponseEntity.ok(leaveBalanceService.listLeaveBalances(employeeId, year));
    }

    /**
     * 잔여 연차 조회.
     */
    @GetMapping("/balance")
    public ResponseEntity<LeaveBalanceResponse> getLeaveBalance(
            @RequestParam @NotNull Integer employeeId,
            @RequestParam(required = false) Integer year
    ) {
        log.info("GET /api/leave/balance - employeeId={}, year={}", employeeId, year);
        return ResponseEntity.ok(leaveBalanceService.getLeaveBalance(employeeId, year));
    }
}

