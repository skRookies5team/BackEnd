package com.AIagnet.agent.meetingroom.dto;

import com.AIagnet.agent.common.dto.ChatResponse;
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
public class MeetingRoomChatResponse implements ChatResponse {

    private String answer;
    private String agent;
}

