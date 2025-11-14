package com.AIagnet.agent.meetingroom.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 예약 생성 요청 DTO (AI-009)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCreateRequest {

    /** 회의실 ID (Integer로 변경) */
    @NotNull(message = "회의실 ID는 필수입니다")
    @Positive(message = "회의실 ID는 양수여야 합니다")
    private Integer meetingRoomId;

    /** 직원 ID (Integer로 변경) */
    @NotNull(message = "직원 ID는 필수입니다")
    @Positive(message = "직원 ID는 양수여야 합니다")
    private Integer employeeId;

    /** 일정 ID (옵션, Integer로 변경) */
    private Integer scheduleId;

    /** 제목 */
    @NotBlank(message = "회의 제목은 필수입니다")
    private String title;

    /** 설명 */
    private String description;

    /** 시작 시간 */
    @NotNull(message = "시작 시간은 필수입니다")
    @FutureOrPresent(message = "시작 시간은 현재 또는 미래여야 합니다")
    private LocalDateTime startTime;

    /** 종료 시간 */
    @NotNull(message = "종료 시간은 필수입니다")
    @FutureOrPresent(message = "종료 시간은 현재 또는 미래여야 합니다")
    private LocalDateTime endTime;

    /** 참석 인원 */
    @Positive(message = "참석 인원은 양수여야 합니다")
    private Integer participants;
}
