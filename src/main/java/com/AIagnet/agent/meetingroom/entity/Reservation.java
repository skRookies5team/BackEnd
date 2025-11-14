package com.AIagnet.agent.meetingroom.entity;

import com.AIagnet.agent.common.entity.Employee;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_room_reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 회의실 ID */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_room_id", nullable = false)
    private MeetingRoom meetingRoom;

    /** 직원 ID */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /** 일정 ID — DB 타입 INT */
    @Column(name = "schedule_id")
    private Integer scheduleId;

    /** 제목 */
    @Column(name = "title", length = 200)
    private String title;

    /** 설명 */
    @Column(name = "description", length = 500)
    private String description;

    /** 시작 시간 */
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /** 종료 시간 */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /** 참석 인원 */
    @Column(name = "participants")
    private Integer participants;

    /** 상태 (CONFIRMED, CANCELLED, PENDING) */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ReservationStatus status;

    /** 상태 ENUM */
    public enum ReservationStatus {
        CONFIRMED, CANCELLED, PENDING
    }
}
