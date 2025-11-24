package com.AIagnet.agent.leave.dto.response;

import com.AIagnet.agent.leave.entity.LeaveRequest;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveRequestStatus;
import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 연차 신청 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LeaveRequestResponse {
    private Integer id;
    private String requestId;
    private Integer employeeId;
    private String employeeName;
    private LocalDate requestDate;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalDays;
    private LeaveRequestStatus status;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LeaveRequestResponse from(LeaveRequest leaveRequest) {
        return LeaveRequestResponse.builder()
                .id(leaveRequest.getId())
                .requestId(leaveRequest.getRequestId())
                .employeeId(leaveRequest.getEmployee().getEmployeeId())
                .employeeName(leaveRequest.getEmployee().getName())
                .requestDate(leaveRequest.getRequestDate())
                .leaveType(leaveRequest.getLeaveType())
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .totalDays(leaveRequest.getTotalDays())
                .status(leaveRequest.getStatus())
                .reason(leaveRequest.getReason())
                .createdAt(leaveRequest.getCreatedAt())
                .updatedAt(leaveRequest.getUpdatedAt())
                .build();
    }
}

