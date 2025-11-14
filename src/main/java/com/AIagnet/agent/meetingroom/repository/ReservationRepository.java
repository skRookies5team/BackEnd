package com.AIagnet.agent.meetingroom.repository;

import com.AIagnet.agent.common.entity.Employee;
import com.AIagnet.agent.meetingroom.entity.MeetingRoom;
import com.AIagnet.agent.meetingroom.entity.Reservation;
import com.AIagnet.agent.meetingroom.entity.Reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByMeetingRoom(MeetingRoom meetingRoom);

    List<Reservation> findByEmployee(Employee employee);

    List<Reservation> findByStatus(ReservationStatus status);

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.meetingRoom.id = :meetingRoomId
              AND r.status = :status
              AND r.startTime < :endTime
              AND r.endTime > :startTime
            ORDER BY r.startTime ASC
            """)
    List<Reservation> findByMeetingRoomAndTimeRange(
            @Param("meetingRoomId") Integer meetingRoomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") ReservationStatus status
    );

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.status = 'CONFIRMED'
              AND r.startTime < :endTime
              AND r.endTime > :startTime
            ORDER BY r.startTime ASC
            """)
    List<Reservation> findConfirmedReservationsInRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.employee.employeeId = :employeeId
              AND r.status = :status
              AND r.startTime < :endTime
              AND r.endTime > :startTime
            ORDER BY r.startTime ASC
            """)
    List<Reservation> findByEmployeeAndTimeRange(
            @Param("employeeId") Integer employeeId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") ReservationStatus status
    );

    @Query("""
            SELECT COUNT(r) > 0 FROM Reservation r
            WHERE r.meetingRoom.id = :meetingRoomId
              AND r.status = 'CONFIRMED'
              AND r.startTime < :endTime
              AND r.endTime > :startTime
            """)
    boolean existsOverlappingReservation(
            @Param("meetingRoomId") Integer meetingRoomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.meetingRoom.building = :building
              AND r.status = :status
              AND r.startTime < :endTime
              AND r.endTime > :startTime
            ORDER BY r.startTime ASC
            """)
    List<Reservation> findByBuildingAndTimeRange(
            @Param("building") String building,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") ReservationStatus status
    );

    List<Reservation> findByTitleContaining(String title);

    List<Reservation> findByScheduleId(Integer scheduleId);
}