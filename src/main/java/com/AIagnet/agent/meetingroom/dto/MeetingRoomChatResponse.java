package com.AIagnet.agent.meetingroom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회의실 챗봇 응답 DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomChatResponse {

    private String answer;
    private String agent;
}

