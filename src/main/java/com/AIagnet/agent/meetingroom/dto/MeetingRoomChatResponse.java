package com.AIagnet.agent.meetingroom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * FastAPI 회의실 챗봇 응답 DTO.
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeetingRoomChatResponse {

    /**
     * 성공 여부.
     */
    @JsonProperty("ok")
    private boolean success;

    /**
     * 요약 메시지.
     */
    @JsonProperty("message")
    private String message;

    /**
     * 회의실 데이터 행.
     */
    @JsonProperty("rows")
    private List<Map<String, Object>> rows;

    /**
     * 오류 메시지 (성공 시 null).
     */
    @JsonProperty("error")
    private String error;
}

