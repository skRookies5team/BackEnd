package com.AIagnet.agent.aiagent.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * AI 에이전트 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentRequest {

    /**
     * 사용자 자연어 입력
     */
    @NotBlank(message = "메시지는 필수입니다")
    private String message;

    /**
     * 추가 컨텍스트 정보 (옵션)
     * 예: employeeId, startTime, endTime 등
     */
    private Map<String, Object> context;
}