package com.AIagnet.agent.meetingroom.controller;

import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatRequest;
import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatResponse;
import com.AIagnet.agent.meetingroom.dto.request.MeetingRoomSearchRequest;
import com.AIagnet.agent.meetingroom.dto.response.MeetingRoomRecommendResponse;
import com.AIagnet.agent.meetingroom.dto.response.MeetingRoomResponse;
import com.AIagnet.agent.meetingroom.service.MeetingRoomProxyService;
import com.AIagnet.agent.meetingroom.service.MeetingRoomService;
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
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;
    private final MeetingRoomProxyService meetingRoomProxyService;

    /**
     * 회의실 챗봇 POST 엔드포인트 (FastAPI 프록시).
     *
     * @param request 회의실 질의 요청
     * @return FastAPI 응답
     */
    @PostMapping("/chat")
    public ResponseEntity<MeetingRoomChatResponse> chat(@Valid @RequestBody MeetingRoomChatRequest request) {
        log.info("POST /api/meeting-rooms/chat - query={}", request.getQuery());
        MeetingRoomChatResponse response = meetingRoomProxyService.queryMeetingRoom(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 회의실 챗봇 GET 엔드포인트 (질의 파라미터 버전).
     *
     * @param query 질의 문장 (필수)
     * @return FastAPI 응답
     */
    @GetMapping("/chat")
    public ResponseEntity<MeetingRoomChatResponse> getMeetingRoomChat(@RequestParam("query") String query) {
        log.info("GET /api/meeting-rooms/chat - query={}", query);
        MeetingRoomChatRequest request = MeetingRoomChatRequest.builder()
                .query(query)
                .build();
        MeetingRoomChatResponse response = meetingRoomProxyService.queryMeetingRoom(request);
        return ResponseEntity.ok(response);
    }

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
}