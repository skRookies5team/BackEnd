package com.AIagnet.agent.businesstrip.dto;

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
public class BusinessTripChatResponse {

    private String answer;
    private String agent;
}

