package com.AIagnet.agent.leave.dto;

import com.AIagnet.agent.common.dto.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 연차 챗봇 응답 DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveChatResponse implements ChatResponse {

    private String answer;
    private String agent;
}

