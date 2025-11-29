package com.AIagnet.agent.common.controller;

import com.AIagnet.agent.common.dto.UnifiedChatRequest;
import com.AIagnet.agent.common.dto.UnifiedChatResponse;
import com.AIagnet.agent.common.service.FastApiClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 통합 챗봇 컨트롤러.
 * 모든 에이전트 타입의 챗봇 요청을 처리하는 단일 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "AI 챗봇", description = "통합 AI 챗봇 API - 모든 에이전트 타입 지원")
public class ChatController {

    private final FastApiClient fastApiClient;

    /**
     * 통합 챗봇 요청.
     * agentType에 따라 적절한 에이전트로 요청을 라우팅합니다.
     *
     * @param request 통합 챗봇 요청 (agentType, query, employeeId 포함)
     * @return 챗봇 응답
     */
    @PostMapping
    @Operation(
            summary = "통합 챗봇 요청",
            description = "모든 에이전트 타입(schedule, business-trip, leave, meeting-room)의 챗봇 요청을 처리합니다. " +
                    "agentType 필드에 원하는 에이전트 타입을 지정하세요."
    )
    public ResponseEntity<UnifiedChatResponse> chat(
            @Valid @RequestBody UnifiedChatRequest request
    ) {
        log.info("POST /api/chat - agentType={}, query={}, employeeId={}",
                request.getAgentType(), request.getQuery(), request.getEmployeeId());

        UnifiedChatResponse response = fastApiClient.chat(request.getAgentType(), request);
        return ResponseEntity.ok(response);
    }
}

