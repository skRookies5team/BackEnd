package com.AIagnet.agent.aiagent.service;

import com.AIagnet.agent.aiagent.dto.AgentRequest;
import com.AIagnet.agent.aiagent.dto.AgentResponse;
import com.AIagnet.agent.exception.BusinessException;
import com.AIagnet.agent.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentProxyService {

    private final RestTemplate restTemplate;

    /**
     * Python FastAPI 서버 기본 URL (예: http://localhost:8000)
     * application.yml 등에서 ai.agent.url 로 재정의 가능
     */
    @Value("${ai.agent.url:http://localhost:8000}")
    private String aiAgentBaseUrl;

    private URI meetingRoomAgentUri() {
        return URI.create(aiAgentBaseUrl + "/agent/meeting-room/");
    }

    private URI healthUri() {
        return URI.create(aiAgentBaseUrl + "/");
    }

    public AgentResponse sendToAIAgent(AgentRequest request) {
        log.info("AI 에이전트 요청 전달: message={}", request.getMessage());
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("message", request.getMessage());
            if (request.getContext() != null) {
                requestBody.put("context", request.getContext());
            }

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    meetingRoomAgentUri(), entity, Map.class);

            Map<String, Object> body = response.getBody();
            if (body == null) {
                throw new BusinessException(ErrorCode.AI_AGENT_RESPONSE_ERROR, "AI 에이전트 응답이 비어있습니다.");
            }

            String intent = (String) body.get("intent");
            String responseText = (String) body.get("response");
            Map<String, Object> state = (Map<String, Object>) body.get("state");

            log.info("AI 에이전트 응답 수신: intent={}, response={}", intent, responseText);
            return AgentResponse.builder()
                    .intent(intent)
                    .response(responseText)
                    .state(state)
                    .build();

        } catch (RestClientException e) {
            log.error("AI 에이전트 서버 연결 실패: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.AI_AGENT_CONNECTION_FAILED, e);
        } catch (Exception e) {
            log.error("AI 에이전트 응답 처리 중 오류: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.AI_AGENT_RESPONSE_ERROR, e);
        }
    }

    public boolean isAIAgentHealthy() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(healthUri(), String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.warn("AI 에이전트 서버 헬스 체크 실패: {}", e.getMessage());
            return false;
        }
    }
}