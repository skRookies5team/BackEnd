package com.AIagnet.agent.businesstrip.service;

import com.AIagnet.agent.businesstrip.dto.BusinessTripChatRequest;
import com.AIagnet.agent.businesstrip.dto.BusinessTripChatResponse;
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
import java.util.Optional;

/**
 * FastAPI 출장 챗봇 연동 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessTripProxyService {

    private final RestTemplate restTemplate;

    /**
     * FastAPI 출장 챗봇 기본 URL.
     */
    @Value("${ai.business-trip.url:http://localhost:8000}")
    private String businessTripBaseUrl;

    private URI businessTripChatUri() {
        return URI.create(businessTripBaseUrl + "/api/business-trip/chat");
    }

    /**
     * FastAPI 출장 챗봇에 질의를 전송한다.
     *
     * @param request 출장 질의 요청
     * @return 출장 챗봇 응답
     */
    public BusinessTripChatResponse queryBusinessTrip(BusinessTripChatRequest request) {
        log.info("출장 챗봇 요청 전달: query={}", request.getQuery());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<BusinessTripChatRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<BusinessTripChatResponse> response = restTemplate.postForEntity(
                    businessTripChatUri(), entity, BusinessTripChatResponse.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BusinessException(
                        ErrorCode.AI_AGENT_RESPONSE_ERROR,
                        "FastAPI 출장 챗봇 호출이 실패했습니다. status=" + response.getStatusCode()
                );
            }

            BusinessTripChatResponse body = Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new BusinessException(
                            ErrorCode.AI_AGENT_RESPONSE_ERROR,
                            "FastAPI 출장 챗봇 응답이 비어 있습니다."
                    ));

            log.info("출장 챗봇 응답 수신: success={}, message={}", body.isSuccess(), body.getMessage());
            return body;
        } catch (RestClientException e) {
            log.error("FastAPI 출장 챗봇 연결 실패: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.AI_AGENT_CONNECTION_FAILED, e);
        }
    }
}
