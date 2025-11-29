package com.AIagnet.agent.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 통합 챗봇 요청 DTO.
 * 모든 에이전트 타입에 공통으로 사용됩니다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedChatRequest implements ChatRequest {

    /**
     * 에이전트 타입 (예: "schedule", "business-trip", "leave", "meeting-room").
     */
    @NotBlank(message = "에이전트 타입은 필수입니다.")
    private String agentType;

    /**
     * 사용자 질문.
     */
    @NotBlank(message = "질문은 필수입니다.")
    private String query;

    /**
     * 직원 ID (선택사항).
     */
    private Integer employeeId;
}

