package com.AIagnet.agent.meetingroom.dto;

import com.AIagnet.agent.common.dto.ChatRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회의실 챗봇 요청 DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomChatRequest implements ChatRequest {

    @NotBlank(message = "질문은 필수입니다.")
    private String query;

    private Integer employeeId;
}

