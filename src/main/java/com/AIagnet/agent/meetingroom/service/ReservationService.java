package com.AIagnet.agent.meetingroom.service;

import com.AIagnet.agent.common.entity.Employee;
import com.AIagnet.agent.common.repository.EmployeeRepository;
import com.AIagnet.agent.meetingroom.dto.request.ReservationCreateRequest;
import com.AIagnet.agent.meetingroom.dto.request.ReservationSearchRequest;
import com.AIagnet.agent.meetingroom.dto.request.ReservationUpdateRequest;
import com.AIagnet.agent.meetingroom.dto.response.ReservationResponse;
import com.AIagnet.agent.meetingroom.entity.MeetingRoom;
import com.AIagnet.agent.meetingroom.entity.Reservation;
import com.AIagnet.agent.meetingroom.entity.Reservation.ReservationStatus;
import com.AIagnet.agent.meetingroom.repository.MeetingRoomRepository;
import com.AIagnet.agent.meetingroom.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * 예약 생성
     */
    @Transactional
    public ReservationResponse createReservation(ReservationCreateRequest request) {
        log.info("예약 생성 요청: request={}", request);

        validateTimeRange(request.getStartTime(), request.getEndTime());

        // 회의실 조회 (Integer 타입으로 변경)
        MeetingRoom meetingRoom = meetingRoomRepository.findById(request.getMeetingRoomId().intValue())
                .orElseThrow(() -> new IllegalArgumentException(
                        "회의실을 찾을 수 없습니다. id=" + request.getMeetingRoomId()));

        // 직원 조회 (Employee PK도 Integer)
        Employee employee = employeeRepository.findById(request.getEmployeeId().intValue())
                .orElseThrow(() -> new IllegalArgumentException(
                        "직원을 찾을 수 없습니다. id=" + request.getEmployeeId()));

        // 중복 예약 체크
        boolean isOverlapping = reservationRepository.existsOverlappingReservation(
                request.getMeetingRoomId().intValue(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (isOverlapping) {
            throw new IllegalStateException("해당 시간에 이미 예약이 존재합니다.");
        }

        // 인원 체크
        if (request.getParticipants() != null &&
                request.getParticipants() > meetingRoom.getCapacity()) {
            throw new IllegalArgumentException(
                    "참석 인원이 회의실 수용 인원을 초과합니다.");
        }

        // 예약 생성
        Reservation reservation = Reservation.builder()
                .meetingRoom(meetingRoom)
                .employee(employee)
                .scheduleId(request.getScheduleId() != null ? request.getScheduleId().intValue() : null)
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .participants(request.getParticipants())
                .status(ReservationStatus.CONFIRMED)
                .build();

        Reservation saved = reservationRepository.save(reservation);

        return ReservationResponse.from(saved);
    }

    /**
     * 예약 상세 조회
     */
    public ReservationResponse getReservationById(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다. id=" + id));

        return ReservationResponse.from(reservation);
    }

    /**
     * 예약 검색
     */
    public List<ReservationResponse> searchReservations(ReservationSearchRequest request) {

        List<Reservation> reservations;

        // 시간 + 회의실ID
        if (request.getStartTime() != null && request.getEndTime() != null && request.getMeetingRoomId() != null) {
            ReservationStatus status = request.getStatus() != null ? request.getStatus() : ReservationStatus.CONFIRMED;

            reservations = reservationRepository.findByMeetingRoomAndTimeRange(
                    request.getMeetingRoomId().intValue(),
                    request.getStartTime(),
                    request.getEndTime(),
                    status
            );
        }
        // 시간 + 직원ID
        else if (request.getStartTime() != null && request.getEndTime() != null && request.getEmployeeId() != null) {
            ReservationStatus status = request.getStatus() != null ? request.getStatus() : ReservationStatus.CONFIRMED;

            reservations = reservationRepository.findByEmployeeAndTimeRange(
                    request.getEmployeeId().intValue(),
                    request.getStartTime(),
                    request.getEndTime(),
                    status
            );
        }
        // 시간 + 건물
        else if (request.getStartTime() != null && request.getEndTime() != null && request.getBuilding() != null) {
            ReservationStatus status = request.getStatus() != null ? request.getStatus() : ReservationStatus.CONFIRMED;

            reservations = reservationRepository.findByBuildingAndTimeRange(
                    request.getBuilding(),
                    request.getStartTime(),
                    request.getEndTime(),
                    status
            );
        }
        // 시간 범위
        else if (request.getStartTime() != null && request.getEndTime() != null) {
            reservations = reservationRepository.findConfirmedReservationsInRange(
                    request.getStartTime(),
                    request.getEndTime()
            );
        }
        // 회의실 ID
        else if (request.getMeetingRoomId() != null) {
            MeetingRoom room = meetingRoomRepository.findById(request.getMeetingRoomId().intValue())
                    .orElseThrow(() -> new IllegalArgumentException("회의실을 찾을 수 없습니다."));

            reservations = reservationRepository.findByMeetingRoom(room);
        }
        // 직원 ID
        else if (request.getEmployeeId() != null) {
            Employee emp = employeeRepository.findById(request.getEmployeeId().intValue())
                    .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다."));

            reservations = reservationRepository.findByEmployee(emp);
        }
        // 상태 검색
        else if (request.getStatus() != null) {
            reservations = reservationRepository.findByStatus(request.getStatus());
        }
        // 제목 검색
        else if (request.getTitleKeyword() != null && !request.getTitleKeyword().isBlank()) {
            reservations = reservationRepository.findByTitleContaining(request.getTitleKeyword());
        }
        // 전체 조회
        else {
            reservations = reservationRepository.findAll();
        }

        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 예약 수정
     */
    @Transactional
    public ReservationResponse updateReservation(Integer id, ReservationUpdateRequest request) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다. id=" + id));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("취소된 예약은 수정할 수 없습니다.");
        }

        LocalDateTime newStart = request.getStartTime() != null ? request.getStartTime() : reservation.getStartTime();
        LocalDateTime newEnd = request.getEndTime() != null ? request.getEndTime() : reservation.getEndTime();

        if (request.getStartTime() != null || request.getEndTime() != null) {

            validateTimeRange(newStart, newEnd);

            boolean isOverlapping = reservationRepository
                    .findByMeetingRoomAndTimeRange(
                            reservation.getMeetingRoom().getId(),
                            newStart,
                            newEnd,
                            ReservationStatus.CONFIRMED
                    )
                    .stream()
                    .anyMatch(r -> !r.getId().equals(id));

            if (isOverlapping) {
                throw new IllegalStateException("해당 시간에 이미 예약이 존재합니다.");
            }
        }

        if (request.getParticipants() != null &&
                request.getParticipants() > reservation.getMeetingRoom().getCapacity()) {

            throw new IllegalArgumentException("참석 인원이 수용 인원 초과.");
        }

        if (request.getTitle() != null) reservation.setTitle(request.getTitle());
        if (request.getDescription() != null) reservation.setDescription(request.getDescription());
        if (request.getStartTime() != null) reservation.setStartTime(request.getStartTime());
        if (request.getEndTime() != null) reservation.setEndTime(request.getEndTime());
        if (request.getParticipants() != null) reservation.setParticipants(request.getParticipants());

        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    /**
     * 예약 취소
     */
    @Transactional
    public ReservationResponse cancelReservation(Integer id, String reason) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다. id=" + id));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        if (reason != null && !reason.isBlank()) {
            String desc = reservation.getDescription() == null ? "" : reservation.getDescription() + "\n";
            reservation.setDescription(desc + "[취소 사유] " + reason);
        }

        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    /**
     * 예약 삭제
     */
    @Transactional
    public void deleteReservation(Integer id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("예약을 찾을 수 없습니다. id=" + id);
        }
        reservationRepository.deleteById(id);
    }

    /** 시간 검증 */
    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 빨라야 합니다.");
        }
    }
}
