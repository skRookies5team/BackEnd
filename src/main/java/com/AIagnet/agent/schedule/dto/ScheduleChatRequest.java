package com.AIagnet.agent.schedule.dto;

import com.AIagnet.agent.common.dto.ChatRequest;
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
public class ScheduleChatRequest implements ChatRequest {

    @NotBlank(message = "질문은 필수입니다.")
    private String query;

    private Integer employeeId;
}

