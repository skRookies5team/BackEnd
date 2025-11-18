package com.AIagnet.agent.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * FastAPI 일정 챗봇 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ScheduleChatRequest {

    /**
     * 질의 문장 (예: "사번 4001 일정 알려줘").
     */
    @NotBlank(message = "query는 필수 값입니다.")
    private String query;
}


