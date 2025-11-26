package com.AIagnet.agent.schedule.controller;

import com.AIagnet.agent.schedule.dto.ScheduleChatRequest;
import com.AIagnet.agent.schedule.dto.ScheduleChatResponse;
import com.AIagnet.agent.schedule.dto.request.ScheduleCancelRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleCreateRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleRecommendRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleSearchRequest;
import com.AIagnet.agent.schedule.dto.response.ScheduleRecommendResponse;
import com.AIagnet.agent.schedule.dto.response.ScheduleResponse;
import com.AIagnet.agent.schedule.service.ScheduleProxyService;
import com.AIagnet.agent.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 일정 관리 API (챗봇 + CRUD).
 */
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "일정 관리", description = "일정 조회, 생성, 취소, AI 챗봇 API")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleProxyService scheduleProxyService;

    // ========== 일정 관리 CRUD 엔드포인트 ==========

    /**
     * 일정 목록 조회.
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getSchedules(
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) String status
    ) {
        log.info("GET /api/schedule - employeeId={}, startTime={}, endTime={}, status={}",
                employeeId, startTime, endTime, status);
        ScheduleSearchRequest request = ScheduleSearchRequest.builder()
                .employeeId(employeeId)
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .build();
        return ResponseEntity.ok(scheduleService.scanSchedules(request));
    }

    /**
     * 일정 등록.
     */
    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleCreateRequest request) {
        log.info("POST /api/schedule - employeeId={}, title={}", request.getEmployeeId(), request.getTitle());
        return ResponseEntity.ok(scheduleService.createSchedule(request));
    }

    /**
     * 일정 취소.
     */
    @PostMapping("/{scheduleId}/cancel")
    public ResponseEntity<ScheduleResponse> cancelSchedule(
            @PathVariable Integer scheduleId,
            @Valid @RequestBody ScheduleCancelRequest request
    ) {
        log.info("POST /api/schedule/{}/cancel", scheduleId);
        return ResponseEntity.ok(scheduleService.cancelSchedule(scheduleId, request));
    }

    /**
     * 일정 추천.
     */
    @PostMapping("/recommend")
    public ResponseEntity<ScheduleRecommendResponse> recommendSchedule(
            @Valid @RequestBody ScheduleRecommendRequest request
    ) {
        log.info("POST /api/schedule/recommend - employeeId={}", request.getEmployeeId());
        return ResponseEntity.ok(scheduleService.recommendSchedule(request));
    }

    // ========== 일정 챗봇 엔드포인트 ==========

    /**
     * 일정 챗봇 (FastAPI 연동).
     */
    @PostMapping("/chat")
    public ResponseEntity<ScheduleChatResponse> chat(
            @Valid @RequestBody ScheduleChatRequest request
    ) {
        log.info("POST /api/schedule/chat - query={}, employeeId={}", request.getQuery(), request.getEmployeeId());
        return ResponseEntity.ok(scheduleProxyService.chat(request));
    }
}

