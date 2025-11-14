package com.AIagnet.agent.meetingroom.dto.response;

import com.AIagnet.agent.meetingroom.entity.Reservation;
import com.AIagnet.agent.meetingroom.entity.Reservation.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

    /** 예약 ID */
    private Integer id;

    /** 회의실 정보 */
    private MeetingRoomResponse meetingRoom;

    /** 예약 직원 ID */
    private Integer employeeId;

    /** 직원 이름 */
    private String employeeName;

    /** 일정 ID */
    private Integer scheduleId;

    /** 제목 */
    private String title;

    /** 설명 */
    private String description;

    /** 시작 시간 */
    private LocalDateTime startTime;

    /** 종료 시간 */
    private LocalDateTime endTime;

    /** 참석 인원 */
    private Integer participants;

    /** 상태 */
    private ReservationStatus status;

    /** 엔티티 → DTO 변환 */
    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .meetingRoom(MeetingRoomResponse.from(reservation.getMeetingRoom()))
                .employeeId(reservation.getEmployee().getEmployeeId())   // ★ 수정된 부분
                .employeeName(reservation.getEmployee().getName())
                .scheduleId(reservation.getScheduleId())
                .title(reservation.getTitle())
                .description(reservation.getDescription())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .participants(reservation.getParticipants())
                .status(reservation.getStatus())
                .build();
    }
}
