package com.AIagnet.agent.businesstrip.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 출장 조회 요청 DTO.
 */
@Getter
@Builder
@ToString
public class BusinessTripSearchRequest {
    private Integer employeeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}


