package com.AIagnet.agent.schedule.service;

import com.AIagnet.agent.exception.BusinessException;
import com.AIagnet.agent.exception.ErrorCode;
import com.AIagnet.agent.schedule.dto.ScheduleChatRequest;
import com.AIagnet.agent.schedule.dto.ScheduleChatResponse;
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
import java.util.Optional;

/**
 * FastAPI 일정 챗봇 연동 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleProxyService {

    private final RestTemplate restTemplate;

    /**
     * FastAPI 일정 챗봇 기본 URL.
     */
    @Value("${ai.schedule.url:http://localhost:8000}")
    private String scheduleBaseUrl;

    private URI scheduleChatUri() {
        return URI.create(scheduleBaseUrl + "/api/schedule/chat");
    }

    /**
     * FastAPI 일정 챗봇에 질의를 전송한다.
     *
     * @param request 일정 질의 요청
     * @return 일정 챗봇 응답
     */
    public ScheduleChatResponse querySchedule(ScheduleChatRequest request) {
        log.info("일정 챗봇 요청 전달: query={}", request.getQuery());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ScheduleChatRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<ScheduleChatResponse> response = restTemplate.postForEntity(
                    scheduleChatUri(), entity, ScheduleChatResponse.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BusinessException(
                        ErrorCode.AI_AGENT_RESPONSE_ERROR,
                        "FastAPI 일정 챗봇 호출이 실패했습니다. status=" + response.getStatusCode()
                );
            }

            ScheduleChatResponse body = Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new BusinessException(
                            ErrorCode.AI_AGENT_RESPONSE_ERROR,
                            "FastAPI 일정 챗봇 응답이 비어 있습니다."
                    ));

            log.info("일정 챗봇 응답 수신: success={}, message={}", body.isSuccess(), body.getMessage());
            return body;
        } catch (RestClientException e) {
            log.error("FastAPI 일정 챗봇 연결 실패: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.AI_AGENT_CONNECTION_FAILED, e);
        }
    }
}

