package com.AIagnet.agent.schedule.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 일정 추천 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ScheduleRecommendRequest {
    @NotNull(message = "employeeId는 필수 값입니다.")
    private Integer employeeId;

    @NotNull(message = "preferredStart는 필수 값입니다.")
    private LocalDateTime preferredStart;

    private LocalDateTime preferredEnd;

    private Integer durationMinutes;
}

