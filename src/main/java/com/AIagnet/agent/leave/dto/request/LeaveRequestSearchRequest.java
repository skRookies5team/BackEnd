package com.AIagnet.agent.leave.dto.request;

import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveRequestStatus;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 연차 신청 조회 요청 DTO.
 */
@Getter
@Builder
@ToString
public class LeaveRequestSearchRequest {
    private Integer employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveRequestStatus status;
    private LeaveType leaveType;
}

