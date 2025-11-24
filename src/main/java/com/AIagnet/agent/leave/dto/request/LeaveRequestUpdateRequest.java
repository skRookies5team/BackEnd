package com.AIagnet.agent.leave.dto.request;

import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveRequestStatus;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 연차 신청 수정 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class LeaveRequestUpdateRequest {
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalDays;
    private LeaveRequestStatus status;
    private String reason;
}

