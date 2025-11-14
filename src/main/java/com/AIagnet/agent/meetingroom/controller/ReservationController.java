package com.AIagnet.agent.meetingroom.controller;

import com.AIagnet.agent.meetingroom.dto.request.ReservationCancelRequest;
import com.AIagnet.agent.meetingroom.dto.request.ReservationCreateRequest;
import com.AIagnet.agent.meetingroom.dto.request.ReservationSearchRequest;
import com.AIagnet.agent.meetingroom.dto.request.ReservationUpdateRequest;
import com.AIagnet.agent.meetingroom.dto.response.ReservationResponse;
import com.AIagnet.agent.meetingroom.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 예약 컨트롤러
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 생성 (AI-009)
     */
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationCreateRequest request) {
        log.info("POST /api/reservations - 예약 생성: {}", request);
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 예약 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Integer id) {
        log.info("GET /api/reservations/{} - 예약 상세 조회", id);
        ReservationResponse response = reservationService.getReservationById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 예약 검색
     */
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> searchReservations(
            @ModelAttribute ReservationSearchRequest request) {
        log.info("GET /api/reservations - 예약 검색: {}", request);
        List<ReservationResponse> responses = reservationService.searchReservations(request);
        return ResponseEntity.ok(responses);
    }

    /**
     * 예약 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Integer id,
            @Valid @RequestBody ReservationUpdateRequest request) {
        log.info("PUT /api/reservations/{} - 예약 수정: {}", id, request);
        ReservationResponse response = reservationService.updateReservation(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 예약 취소 (AI-009)
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(
            @PathVariable Integer id,
            @RequestBody(required = false) ReservationCancelRequest request) {
        log.info("POST /api/reservations/{}/cancel - 예약 취소", id);
        String reason = request != null ? request.getReason() : null;
        ReservationResponse response = reservationService.cancelReservation(id, reason);
        return ResponseEntity.ok(response);
    }

    /**
     * 예약 삭제 (물리 삭제)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        log.info("DELETE /api/reservations/{} - 예약 삭제", id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}