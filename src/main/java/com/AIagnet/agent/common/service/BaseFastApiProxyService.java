package com.AIagnet.agent.common.service;

import com.AIagnet.agent.common.dto.ChatRequest;
import com.AIagnet.agent.common.dto.ChatResponse;
import com.AIagnet.agent.common.dto.UnifiedChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * FastAPI 프록시 서비스 베이스 클래스.
 * 모든 챗봇 프록시 서비스의 공통 로직을 제공합니다.
 * 내부적으로 FastApiClient를 사용하여 FastAPI와 통신합니다.
 *
 * @param <TRequest>  챗봇 요청 타입
 * @param <TResponse> 챗봇 응답 타입
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseFastApiProxyService<TRequest extends ChatRequest, TResponse extends ChatResponse> {

    protected final FastApiClient fastApiClient;
    protected final ObjectMapper objectMapper;

    /**
     * 에이전트 타입을 반환합니다 (예: "schedule", "business-trip").
     * 각 서브클래스에서 구현해야 합니다.
     *
     * @return 에이전트 타입
     */
    protected abstract String getAgentType();

    /**
     * 챗봇 요청을 FastAPI로 전달합니다.
     *
     * @param request 챗봇 요청
     * @return 챗봇 응답
     */
    public TResponse chat(TRequest request) {
        UnifiedChatResponse unifiedResponse = fastApiClient.chat(getAgentType(), request);
        return convertToResponse(unifiedResponse);
    }

    /**
     * UnifiedChatResponse를 특정 응답 타입으로 변환합니다.
     *
     * @param unifiedResponse 통합 응답
     * @return 변환된 응답
     */
    protected TResponse convertToResponse(UnifiedChatResponse unifiedResponse) {
        try {
            return objectMapper.convertValue(unifiedResponse, getResponseClass());
        } catch (Exception e) {
            log.error("응답 변환 실패: {}", e.getMessage(), e);
            // 기본 변환이 실패하면 수동으로 생성
            return createResponseFromUnified(unifiedResponse);
        }
    }

    /**
     * UnifiedChatResponse로부터 응답 객체를 생성합니다.
     * 서브클래스에서 필요시 오버라이드할 수 있습니다.
     *
     * @param unifiedResponse 통합 응답
     * @return 생성된 응답
     */
    @SuppressWarnings("unchecked")
    protected TResponse createResponseFromUnified(UnifiedChatResponse unifiedResponse) {
        try {
            TResponse response = getResponseClass().getDeclaredConstructor().newInstance();
            // 리플렉션을 사용하여 answer와 agent 필드 설정
            var answerField = getResponseClass().getDeclaredField("answer");
            answerField.setAccessible(true);
            answerField.set(response, unifiedResponse.getAnswer());

            try {
                var agentField = getResponseClass().getDeclaredField("agent");
                agentField.setAccessible(true);
                agentField.set(response, unifiedResponse.getAgent());
            } catch (NoSuchFieldException ignored) {
                // agent 필드가 없으면 무시
            }

            return response;
        } catch (Exception e) {
            log.error("응답 객체 생성 실패: {}", e.getMessage(), e);
            throw new RuntimeException("응답 변환 실패", e);
        }
    }

    /**
     * 응답 클래스를 반환합니다.
     * 각 서브클래스에서 구현해야 합니다.
     *
     * @return 응답 클래스
     */
    protected abstract Class<TResponse> getResponseClass();
}

