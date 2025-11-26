package com.AIagnet.agent.meetingroom.service;

import com.AIagnet.agent.exception.BusinessException;
import com.AIagnet.agent.exception.ErrorCode;
import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatRequest;
import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatResponse;
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

/**
 * 회의실 챗봇 프록시 서비스 (FastAPI 연동).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingRoomProxyService {

    private final RestTemplate restTemplate;

    @Value("${ai.fastapi.url}")
    private String fastApiUrl;

    /**
     * 회의실 챗봇 요청을 FastAPI로 전달.
     *
     * @param request 챗봇 요청
     * @return 챗봇 응답
     */
    public MeetingRoomChatResponse chat(MeetingRoomChatRequest request) {
        String url = fastApiUrl + "/api/agents/meeting-room/chat";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HashMap<String, Object> body = new HashMap<>();
            body.put("query", request.getQuery());
            if (request.getEmployeeId() != null) {
                body.put("employee_id", request.getEmployeeId());
            }

            HttpEntity<HashMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

            log.info("FastAPI 요청: {} - query={}, employeeId={}", url, request.getQuery(), request.getEmployeeId());

            ResponseEntity<MeetingRoomChatResponse> response = restTemplate.postForEntity(
                    url, httpEntity, MeetingRoomChatResponse.class);

            log.info("FastAPI 응답: status={}, answer={}", response.getStatusCode(), response.getBody());

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("FastAPI HTTP 오류: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new BusinessException(ErrorCode.AI_AGENT_RESPONSE_ERROR);
        } catch (RestClientException e) {
            log.error("FastAPI 연결 오류: url={}", url, e);
            throw new BusinessException(ErrorCode.AI_AGENT_CONNECTION_FAILED);
        }
    }
}

