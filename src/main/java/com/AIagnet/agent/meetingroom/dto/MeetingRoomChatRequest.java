package com.AIagnet.agent.meetingroom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * FastAPI 회의실 챗봇 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class MeetingRoomChatRequest {

    /**
     * 질의 문장 (예: "내일 오전 10시에 5명이 쓸 회의실 추천해줘").
     */
    @NotBlank(message = "query는 필수 값입니다.")
    private String query;
}

