package com.AIagnet.agent.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 통합 챗봇 응답 DTO.
 * 모든 에이전트 타입에 공통으로 사용됩니다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedChatResponse implements ChatResponse {

    /**
     * 챗봇 응답 텍스트.
     */
    private String answer;

    /**
     * 에이전트 타입.
     */
    private String agent;
}

