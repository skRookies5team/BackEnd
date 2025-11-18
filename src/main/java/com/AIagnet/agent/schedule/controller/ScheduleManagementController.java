package com.AIagnet.agent.schedule.controller;

import com.AIagnet.agent.schedule.dto.request.ScheduleCancelRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleCreateRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleRecommendRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleSearchRequest;
import com.AIagnet.agent.schedule.dto.response.ScheduleRecommendResponse;
import com.AIagnet.agent.schedule.dto.response.ScheduleResponse;
import com.AIagnet.agent.schedule.entity.ScheduleStatus;

import com.AIagnet.agent.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
 * 사내 일정 관리 컨트롤러.
 */
@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@Slf4j
public class ScheduleManagementController {

    private final ScheduleService scheduleService;

    /**
     * 일정 조회 (스캔).
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> scanSchedules(
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) ScheduleStatus status
    ) {
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
        log.info("POST /api/schedules - employeeId={}, title={}", request.getEmployeeId(), request.getTitle());
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
        log.info("POST /api/schedules/{}/cancel", scheduleId);
        return ResponseEntity.ok(scheduleService.cancelSchedule(scheduleId, request));
    }

    /**
     * 일정 추천.
     */
    @PostMapping("/recommend")
    public ResponseEntity<ScheduleRecommendResponse> recommendSchedule(
            @Valid @RequestBody ScheduleRecommendRequest request
    ) {
        log.info("POST /api/schedules/recommend - employeeId={}", request.getEmployeeId());
        return ResponseEntity.ok(scheduleService.recommendSchedule(request));
    }
}