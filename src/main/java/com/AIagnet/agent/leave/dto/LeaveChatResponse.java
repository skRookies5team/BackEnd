package com.AIagnet.agent.leave.dto;

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
public class LeaveChatResponse {

    private String answer;
    private String agent;
}

