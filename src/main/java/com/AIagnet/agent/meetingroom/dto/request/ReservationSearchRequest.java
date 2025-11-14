package com.AIagnet.agent.meetingroom.dto.request;

import com.AIagnet.agent.meetingroom.entity.Reservation.ReservationStatus;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 예약 검색 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSearchRequest {

    /** 회의실 ID (Integer로 변경) */
    private Integer meetingRoomId;

    /** 직원 ID (Integer로 변경) */
    private Integer employeeId;

    /** 건물명 */
    private String building;

    /** 시작 시간 */
    private LocalDateTime startTime;

    /** 종료 시간 */
    private LocalDateTime endTime;

    /** 예약 상태 */
    private ReservationStatus status;

    /** 제목 검색 키워드 */
    private String titleKeyword;
}
