package com.AIagnet.agent.schedule.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 일정 추천 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleRecommendResponse {
    private boolean hasConflict;
    private Integer conflictingScheduleId;
    private LocalDateTime recommendedStart;
    private LocalDateTime recommendedEnd;
    private String message;
}

