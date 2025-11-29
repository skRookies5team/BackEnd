package com.AIagnet.agent.common.service;

import com.AIagnet.agent.common.dto.ChatRequest;
import com.AIagnet.agent.common.dto.ChatResponse;
import com.AIagnet.agent.common.dto.UnifiedChatResponse;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 공통 FastAPI 클라이언트.
 * 모든 챗봇 요청을 FastAPI 서버로 전달하는 통합 클라이언트입니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FastApiClient {

    private final RestTemplate restTemplate;

    @Value("${ai.fastapi.url}")
    private String fastApiUrl;

    /**
     * 챗봇 요청을 FastAPI로 전달합니다.
     *
     * @param agentType 에이전트 타입 (예: "schedule", "business-trip", "leave", "meeting-room")
     * @param request   챗봇 요청
     * @return 통합 챗봇 응답
     */
    public UnifiedChatResponse chat(String agentType, ChatRequest request) {
        String url = buildUrl(agentType);

        try {
            HttpEntity<Map<String, Object>> httpEntity = createHttpEntity(request);
            logRequest(url, agentType, request);

            ResponseEntity<UnifiedChatResponse> response = restTemplate.postForEntity(
                    url, httpEntity, UnifiedChatResponse.class);

            logResponse(response);
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("FastAPI HTTP 오류: agentType={}, status={}, body={}",
                    agentType, e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new BusinessException(ErrorCode.AI_AGENT_RESPONSE_ERROR);
        } catch (RestClientException e) {
            log.error("FastAPI 연결 오류: agentType={}, url={}", agentType, url, e);
            throw new BusinessException(ErrorCode.AI_AGENT_CONNECTION_FAILED);
        }
    }

    /**
     * FastAPI URL을 생성합니다.
     *
     * @param agentType 에이전트 타입
     * @return 완성된 URL
     */
    private String buildUrl(String agentType) {
        return fastApiUrl + "/api/agents/" + agentType + "/chat";
    }

    /**
     * HTTP 엔티티를 생성합니다.
     *
     * @param request 챗봇 요청
     * @return HTTP 엔티티
     */
    private HttpEntity<Map<String, Object>> createHttpEntity(ChatRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("query", request.getQuery());
        if (request.getEmployeeId() != null) {
            body.put("employee_id", request.getEmployeeId());
        }

        return new HttpEntity<>(body, headers);
    }

    /**
     * 요청 로그를 기록합니다.
     *
     * @param url       요청 URL
     * @param agentType 에이전트 타입
     * @param request   챗봇 요청
     */
    private void logRequest(String url, String agentType, ChatRequest request) {
        log.info("FastAPI 요청: {} - agentType={}, query={}, employeeId={}",
                url, agentType, request.getQuery(), request.getEmployeeId());
    }

    /**
     * 응답 로그를 기록합니다.
     *
     * @param response HTTP 응답
     */
    private void logResponse(ResponseEntity<UnifiedChatResponse> response) {
        log.info("FastAPI 응답: status={}, answer={}", response.getStatusCode(), response.getBody());
    }
}

