package com.AIagnet.agent.leave.controller;

import com.AIagnet.agent.leave.dto.LeaveBalanceResponse;
import com.AIagnet.agent.leave.dto.LeaveChatRequest;
import com.AIagnet.agent.leave.dto.LeaveChatResponse;
import com.AIagnet.agent.leave.dto.request.LeaveRequestCreateRequest;
import com.AIagnet.agent.leave.dto.request.LeaveRequestSearchRequest;
import com.AIagnet.agent.leave.dto.request.LeaveRequestUpdateRequest;
import com.AIagnet.agent.leave.dto.response.LeaveRequestResponse;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveRequestStatus;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveType;
import com.AIagnet.agent.leave.service.LeaveBalanceService;
import com.AIagnet.agent.leave.service.LeaveProxyService;
import com.AIagnet.agent.leave.service.LeaveRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * 연차 관리 API.
 */
@RestController
@RequestMapping("/api/leave")
@RequiredArgsConstructor
@Validated
@Slf4j
public class LeaveController {

    private final LeaveBalanceService leaveBalanceService;
    private final LeaveProxyService leaveProxyService;
    private final LeaveRequestService leaveRequestService;

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

    /**
     * 연차 챗봇 POST 엔드포인트 (FastAPI 프록시).
     *
     * @param request 연차 질의 요청
     * @return FastAPI 응답
     */
    @PostMapping("/chat")
    public ResponseEntity<LeaveChatResponse> chat(@Valid @RequestBody LeaveChatRequest request) {
        log.info("POST /api/leave/chat - query={}", request.getQuery());
        LeaveChatResponse response = leaveProxyService.queryLeave(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 연차 챗봇 GET 엔드포인트 (질의 파라미터 버전).
     *
     * @param query 질의 문장 (필수)
     * @return FastAPI 응답
     */
    @GetMapping("/chat")
    public ResponseEntity<LeaveChatResponse> getLeaveChat(@RequestParam("query") String query) {
        log.info("GET /api/leave/chat - query={}", query);
        LeaveChatRequest request = LeaveChatRequest.builder()
                .query(query)
                .build();
        LeaveChatResponse response = leaveProxyService.queryLeave(request);
        return ResponseEntity.ok(response);
    }

    // ========== 연차 신청 관련 엔드포인트 ==========

    /**
     * 연차 신청 목록 조회.
     */
    @GetMapping("/requests")
    public ResponseEntity<List<LeaveRequestResponse>> searchLeaveRequests(
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) LeaveRequestStatus status,
            @RequestParam(required = false) LeaveType leaveType
    ) {
        log.info("GET /api/leave/requests - employeeId={}, startDate={}, endDate={}, status={}, leaveType={}",
                employeeId, startDate, endDate, status, leaveType);

        LeaveRequestSearchRequest request = LeaveRequestSearchRequest.builder()
                .employeeId(employeeId)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .leaveType(leaveType)
                .build();

        return ResponseEntity.ok(leaveRequestService.searchLeaveRequests(request));
    }

    /**
     * 연차 신청 상세 조회.
     */
    @GetMapping("/requests/{id}")
    public ResponseEntity<LeaveRequestResponse> getLeaveRequestById(@PathVariable Integer id) {
        log.info("GET /api/leave/requests/{}", id);
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestById(id));
    }

    /**
     * 연차 신청 생성.
     */
    @PostMapping("/requests")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(
            @Valid @RequestBody LeaveRequestCreateRequest request
    ) {
        log.info("POST /api/leave/requests - employeeId={}, leaveType={}, startDate={}, endDate={}",
                request.getEmployeeId(), request.getLeaveType(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(leaveRequestService.createLeaveRequest(request));
    }

    /**
     * 연차 신청 수정.
     */
    @PutMapping("/requests/{id}")
    public ResponseEntity<LeaveRequestResponse> updateLeaveRequest(
            @PathVariable Integer id,
            @Valid @RequestBody LeaveRequestUpdateRequest request
    ) {
        log.info("PUT /api/leave/requests/{}", id);
        return ResponseEntity.ok(leaveRequestService.updateLeaveRequest(id, request));
    }

    /**
     * 연차 신청 삭제.
     */
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Integer id) {
        log.info("DELETE /api/leave/requests/{}", id);
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.noContent().build();
    }
}

