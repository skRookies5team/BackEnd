package com.AIagnet.agent.schedule.dto.request;

import com.AIagnet.agent.schedule.entity.ScheduleStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 일정 조회 요청.
 */
@Getter
@Builder
@ToString
public class ScheduleSearchRequest {

    private Integer employeeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ScheduleStatus status;
}



