package com.AIagnet.agent.businesstrip.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 출장 생성 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class BusinessTripCreateRequest {

    @NotNull(message = "employeeId는 필수 값입니다.")
    private Integer employeeId;

    private String tripTitle;

    private String tripPurpose;

    private String destination;

    @NotNull(message = "startTime은 필수 값입니다.")
    private LocalDateTime startTime;

    @NotNull(message = "endTime은 필수 값입니다.")
    private LocalDateTime endTime;

    private LocalDateTime alertTime;
}

