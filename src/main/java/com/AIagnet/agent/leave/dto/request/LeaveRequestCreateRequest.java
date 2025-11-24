package com.AIagnet.agent.leave.dto.request;

import com.AIagnet.agent.leave.entity.LeaveRequest.LeaveType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 연차 신청 생성 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class LeaveRequestCreateRequest {
    @NotNull(message = "employeeId는 필수 값입니다.")
    private Integer employeeId;

    private LocalDate requestDate;

    @NotNull(message = "leaveType은 필수 값입니다.")
    private LeaveType leaveType;

    @NotNull(message = "startDate는 필수 값입니다.")
    private LocalDate startDate;

    @NotNull(message = "endDate는 필수 값입니다.")
    private LocalDate endDate;

    @NotNull(message = "totalDays는 필수 값입니다.")
    private BigDecimal totalDays;

    private String reason;
}

