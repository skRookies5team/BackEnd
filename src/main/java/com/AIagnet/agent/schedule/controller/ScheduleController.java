package com.AIagnet.agent.schedule.controller;

import com.AIagnet.agent.schedule.dto.ScheduleChatRequest;
import com.AIagnet.agent.schedule.dto.ScheduleChatResponse;
import com.AIagnet.agent.schedule.service.ScheduleProxyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일정 챗봇 API.
 */
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleController {

    private final ScheduleProxyService scheduleProxyService;

    /**
     * 일정 챗봇 POST 엔드포인트 (FastAPI 프록시).
     *
     * @param request 일정 질의 요청
     * @return FastAPI 응답
     */
    @PostMapping("/chat")
    public ResponseEntity<ScheduleChatResponse> chat(@Valid @RequestBody ScheduleChatRequest request) {
        log.info("POST /api/schedule/chat - query={}", request.getQuery());
        ScheduleChatResponse response = scheduleProxyService.querySchedule(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 일정 챗봇 GET 엔드포인트 (질의 파라미터 버전).
     *
     * @param query 질의 문장
     * @return FastAPI 응답
     */
    @GetMapping
    public ResponseEntity<ScheduleChatResponse> getSchedule(@RequestParam("query") String query) {
        log.info("GET /api/schedule - query={}", query);
        ScheduleChatRequest request = ScheduleChatRequest.builder()
                .query(query)
                .build();
        ScheduleChatResponse response = scheduleProxyService.querySchedule(request);
        return ResponseEntity.ok(response);
    }
}

