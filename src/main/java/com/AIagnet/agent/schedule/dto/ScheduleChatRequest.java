package com.AIagnet.agent.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 일정 챗봇 요청 DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleChatRequest {

    @NotBlank(message = "질문은 필수입니다.")
    private String query;

    private Integer employeeId;
}

