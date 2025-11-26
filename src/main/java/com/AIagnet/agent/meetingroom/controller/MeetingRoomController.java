package com.AIagnet.agent.meetingroom.controller;

import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatRequest;
import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatResponse;
import com.AIagnet.agent.meetingroom.dto.request.MeetingRoomSearchRequest;
import com.AIagnet.agent.meetingroom.dto.response.MeetingRoomRecommendResponse;
import com.AIagnet.agent.meetingroom.dto.response.MeetingRoomResponse;
import com.AIagnet.agent.meetingroom.service.MeetingRoomProxyService;
import com.AIagnet.agent.meetingroom.service.MeetingRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 회의실 컨트롤러
 */
@RestController
@RequestMapping("/api/meeting-rooms")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Meeting", description = "회의실/회의 일정 API")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;
    private final MeetingRoomProxyService meetingRoomProxyService;

    // ========== 회의실 관리 엔드포인트 ==========

    /**
     * 전체 회의실 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<MeetingRoomResponse>> getAllMeetingRooms() {
        log.info("GET /api/meeting-rooms - 전체 회의실 목록 조회");
        List<MeetingRoomResponse> rooms = meetingRoomService.getAllMeetingRooms();
        return ResponseEntity.ok(rooms);
    }

    /**
     * 회의실 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<MeetingRoomResponse> getMeetingRoomById(@PathVariable Integer id) {
        log.info("GET /api/meeting-rooms/{} - 회의실 상세 조회", id);
        MeetingRoomResponse room = meetingRoomService.getMeetingRoomById(id);
        return ResponseEntity.ok(room);
    }

    /**
     * 회의실 코드로 조회
     */
    @GetMapping("/code/{roomCode}")
    public ResponseEntity<MeetingRoomResponse> getMeetingRoomByCode(@PathVariable String roomCode) {
        log.info("GET /api/meeting-rooms/code/{} - 회의실 코드로 조회", roomCode);
        MeetingRoomResponse room = meetingRoomService.getMeetingRoomByCode(roomCode);
        return ResponseEntity.ok(room);
    }

    /**
     * 건물별 회의실 목록 조회
     */
    @GetMapping("/building/{building}")
    public ResponseEntity<List<MeetingRoomResponse>> getMeetingRoomsByBuilding(@PathVariable String building) {
        log.info("GET /api/meeting-rooms/building/{} - 건물별 회의실 조회", building);
        List<MeetingRoomResponse> rooms = meetingRoomService.getMeetingRoomsByBuilding(building);
        return ResponseEntity.ok(rooms);
    }

    /**
     * 회의실 검색 (AI-007)
     */
    @GetMapping("/search")
    public ResponseEntity<List<MeetingRoomResponse>> searchMeetingRooms(
            @ModelAttribute MeetingRoomSearchRequest request) {
        log.info("GET /api/meeting-rooms/search - 회의실 검색: {}", request);
        List<MeetingRoomResponse> rooms = meetingRoomService.searchMeetingRooms(request);
        return ResponseEntity.ok(rooms);
    }

    /**
     * 회의실 추천 (AI-008)
     */
    @GetMapping("/recommend")
    public ResponseEntity<MeetingRoomRecommendResponse> recommendMeetingRooms(
            @ModelAttribute MeetingRoomSearchRequest request) {
        log.info("GET /api/meeting-rooms/recommend - 회의실 추천: {}", request);
        MeetingRoomRecommendResponse response = meetingRoomService.recommendMeetingRooms(request);
        return ResponseEntity.ok(response);
    }

    // ========== 회의실 챗봇 엔드포인트 ==========

    /**
     * 회의실 챗봇 (FastAPI 연동).
     */
    @PostMapping("/chat")
    public ResponseEntity<MeetingRoomChatResponse> chat(
            @Valid @RequestBody MeetingRoomChatRequest request
    ) {
        log.info("POST /api/meeting-rooms/chat - query={}, employeeId={}", request.getQuery(), request.getEmployeeId());
        return ResponseEntity.ok(meetingRoomProxyService.chat(request));
    }
}