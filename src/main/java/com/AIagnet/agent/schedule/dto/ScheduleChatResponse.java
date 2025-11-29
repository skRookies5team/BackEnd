package com.AIagnet.agent.schedule.dto;

import com.AIagnet.agent.common.dto.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 일정 챗봇 응답 DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleChatResponse implements ChatResponse {

    private String answer;
    private String agent;
}

