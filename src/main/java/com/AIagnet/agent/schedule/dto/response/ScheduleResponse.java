package com.AIagnet.agent.schedule.dto.response;

import com.AIagnet.agent.schedule.entity.Schedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 일정 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleResponse {
    private Integer scheduleId;
    private Integer employeeId;
    private String employeeName;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleType;
    private String location;
    private LocalDateTime alertTime;
    private LocalDateTime createdAt;
    private String status;

    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .employeeId(schedule.getEmployee().getEmployeeId())
                .employeeName(schedule.getEmployee().getName())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .scheduleType(schedule.getScheduleType())
                .location(schedule.getLocation())
                .alertTime(schedule.getAlertTime())
                .createdAt(schedule.getCreatedAt())
                .status(schedule.getStatus())
                .build();
    }
}

