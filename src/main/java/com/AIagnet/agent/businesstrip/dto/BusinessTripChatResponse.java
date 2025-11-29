package com.AIagnet.agent.businesstrip.dto;

import com.AIagnet.agent.common.dto.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 출장 챗봇 응답 DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTripChatResponse implements ChatResponse {

    private String answer;
    private String agent;
}

