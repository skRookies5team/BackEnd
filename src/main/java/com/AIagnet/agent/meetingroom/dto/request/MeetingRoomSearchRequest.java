package com.AIagnet.agent.meetingroom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 회의실 검색 요청 DTO (AI-007)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomSearchRequest {

    /**
     * 시작 시간
     */
    private LocalDateTime startTime;

    /**
     * 종료 시간
     */
    private LocalDateTime endTime;

    /**
     * 최소 수용 인원
     */
    private Integer capacity;

    /**
     * 화상 회의 장비 필요 여부
     */
    private Boolean requireVideo;

    /**
     * 건물명
     */
    private String building;

    /**
     * 층
     */
    private String floor;
}