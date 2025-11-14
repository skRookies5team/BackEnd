package com.AIagnet.agent.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * AI 에이전트 응답 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentResponse {

    /**
     * 추론된 사용자 의도
     */
    private String intent;

    /**
     * AI 에이전트 응답 메시지
     */
    private String response;

    /**
     * 추가 상태 정보 (옵션)
     */
    private Map<String, Object> state;
}