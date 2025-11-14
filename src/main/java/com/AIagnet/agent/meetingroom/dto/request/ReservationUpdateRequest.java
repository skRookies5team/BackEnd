package com.AIagnet.agent.meetingroom.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 예약 수정 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationUpdateRequest {

    /**
     * 회의 제목
     */
    private String title;

    /**
     * 회의 설명
     */
    private String description;

    /**
     * 시작 시간
     */
    @FutureOrPresent(message = "시작 시간은 현재 또는 미래여야 합니다")
    private LocalDateTime startTime;

    /**
     * 종료 시간
     */
    @FutureOrPresent(message = "종료 시간은 현재 또는 미래여야 합니다")
    private LocalDateTime endTime;

    /**
     * 참석 인원 수
     */
    @Positive(message = "참석 인원은 양수여야 합니다")
    private Integer participants;
}