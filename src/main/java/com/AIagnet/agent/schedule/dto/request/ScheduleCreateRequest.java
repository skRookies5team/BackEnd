package com.AIagnet.agent.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 일정 생성 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ScheduleCreateRequest {
    @NotNull(message = "employeeId는 필수 값입니다.")
    private Integer employeeId;

    @NotBlank(message = "title은 필수 값입니다.")
    private String title;

    private String description;

    @NotNull(message = "startTime은 필수 값입니다.")
    private LocalDateTime startTime;

    @NotNull(message = "endTime은 필수 값입니다.")
    private LocalDateTime endTime;

    private String scheduleType;

    private String location;

    private LocalDateTime alertTime;

    @NotNull(message = "confirmed는 필수 값입니다.")
    private Boolean confirmed;
}

