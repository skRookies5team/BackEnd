package com.AIagnet.agent.aiagent.controller;

import com.AIagnet.agent.aiagent.dto.AgentRequest;
import com.AIagnet.agent.aiagent.dto.AgentResponse;
import com.AIagnet.agent.aiagent.service.AgentProxyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AI 에이전트 컨트롤러
 */
@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
@Slf4j
public class AgentController {

    private final AgentProxyService agentProxyService;

    /**
     * AI 자연어 처리 (FE-009: 챗 UI)
     *
     * 요청 예시:
     * {
     *   "message": "내일 오전 10시에 5명이 쓸 회의실 추천해줘",
     *   "context": {
     *     "employeeId": 1
     *   }
     * }
     *
     * 응답 예시:
     * {
     *   "intent": "추천",
     *   "response": "추천 회의실 목록:\n- 본관 3F 회의실A (인원: 10명, 화상: 가능)",
     *   "state": {
     *     "recommended_rooms": [...]
     *   }
     * }
     */
    @PostMapping("/chat")
    public ResponseEntity<AgentResponse> chat(@Valid @RequestBody AgentRequest request) {
        log.info("POST /api/agent/chat - AI 자연어 처리: message={}", request.getMessage());
        AgentResponse response = agentProxyService.sendToAIAgent(request);
        return ResponseEntity.ok(response);
    }

    /**
     * AI 에이전트 헬스 체크
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("GET /api/agent/health - AI 에이전트 헬스 체크");
        boolean isHealthy = agentProxyService.isAIAgentHealthy();

        Map<String, Object> response = Map.of(
                "status", isHealthy ? "UP" : "DOWN",
                "aiAgentConnected", isHealthy
        );

        return ResponseEntity.ok(response);
    }
}