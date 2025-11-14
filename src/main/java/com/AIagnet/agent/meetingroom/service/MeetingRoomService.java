package com.AIagnet.agent.meetingroom.service;

import com.AIagnet.agent.meetingroom.dto.request.MeetingRoomSearchRequest;
import com.AIagnet.agent.meetingroom.dto.response.MeetingRoomRecommendResponse;
import com.AIagnet.agent.meetingroom.dto.response.MeetingRoomResponse;
import com.AIagnet.agent.meetingroom.entity.MeetingRoom;
import com.AIagnet.agent.meetingroom.repository.MeetingRoomRepository;
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
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    /** 전체 조회 */
    public List<MeetingRoomResponse> getAllMeetingRooms() {
        return meetingRoomRepository.findAll().stream()
                .map(MeetingRoomResponse::from)
                .collect(Collectors.toList());
    }

    /** 상세 조회 */
    public MeetingRoomResponse getMeetingRoomById(Integer id) {
        MeetingRoom meetingRoom = meetingRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회의실을 찾을 수 없습니다. id=" + id));

        return MeetingRoomResponse.from(meetingRoom);
    }

    /** 코드 조회 */
    public MeetingRoomResponse getMeetingRoomByCode(String roomCode) {
        MeetingRoom meetingRoom = meetingRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("회의실을 찾을 수 없습니다. roomCode=" + roomCode));
        return MeetingRoomResponse.from(meetingRoom);
    }

    /** 건물별 조회 */
    public List<MeetingRoomResponse> getMeetingRoomsByBuilding(String building) {
        return meetingRoomRepository.findByBuilding(building).stream()
                .map(MeetingRoomResponse::from)
                .collect(Collectors.toList());
    }

    /** 조건 검색 */
    public List<MeetingRoomResponse> searchMeetingRooms(MeetingRoomSearchRequest request) {

        // 시간 조건 → available rooms 검색
        if (request.getStartTime() != null && request.getEndTime() != null) {

            validateTimeRange(request.getStartTime(), request.getEndTime());

            Integer capacity = request.getCapacity() != null ? request.getCapacity() : 1;
            Boolean requireVideo = request.getRequireVideo() != null ? request.getRequireVideo() : false;

            return meetingRoomRepository.findAvailableRooms(
                            request.getStartTime(),
                            request.getEndTime(),
                            capacity,
                            requireVideo,
                            request.getBuilding()
                    )
                    .stream()
                    .map(MeetingRoomResponse::from)
                    .collect(Collectors.toList());
        }

        // 기본 필터링
        List<MeetingRoom> rooms = meetingRoomRepository.findAll();

        if (request.getBuilding() != null && !request.getBuilding().isBlank()) {
            rooms = rooms.stream()
                    .filter(r -> r.getBuilding().equals(request.getBuilding()))
                    .collect(Collectors.toList());
        }

        if (request.getFloor() != null && !request.getFloor().isBlank()) {
            rooms = rooms.stream()
                    .filter(r -> r.getFloor().equals(request.getFloor()))
                    .collect(Collectors.toList());
        }

        if (request.getCapacity() != null) {
            rooms = rooms.stream()
                    .filter(r -> r.getCapacity() >= request.getCapacity())
                    .collect(Collectors.toList());
        }

        if (Boolean.TRUE.equals(request.getRequireVideo())) {
            rooms = rooms.stream()
                    .filter(MeetingRoom::getHasVideo)
                    .collect(Collectors.toList());
        }

        return rooms.stream()
                .map(MeetingRoomResponse::from)
                .collect(Collectors.toList());
    }

    /** 회의실 추천 */
    public MeetingRoomRecommendResponse recommendMeetingRooms(MeetingRoomSearchRequest request) {

        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("추천을 위해서는 시작/종료 시간이 필요합니다.");
        }

        validateTimeRange(request.getStartTime(), request.getEndTime());

        Integer capacity = request.getCapacity() != null ? request.getCapacity() : 2;
        Boolean requireVideo = request.getRequireVideo() != null ? request.getRequireVideo() : false;

        List<MeetingRoom> rooms = meetingRoomRepository.findAvailableRooms(
                request.getStartTime(),
                request.getEndTime(),
                capacity,
                requireVideo,
                request.getBuilding()
        );

        List<MeetingRoomResponse> list = rooms.stream()
                .limit(5)
                .map(MeetingRoomResponse::from)
                .collect(Collectors.toList());

        return MeetingRoomRecommendResponse.builder()
                .recommendedRooms(list)
                .message(buildRecommendMessage(request, list.size()))
                .totalCount(list.size())
                .build();
    }

    /** 시간 유효성 체크 */
    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 빨라야 합니다.");
        }
    }

    /** 추천 메시지 */
    private String buildRecommendMessage(MeetingRoomSearchRequest request, int count) {
        if (count == 0) return "조건에 맞는 회의실이 없습니다.";

        StringBuilder sb = new StringBuilder();
        sb.append(count).append("개의 회의실을 찾았습니다. ");

        if (request.getCapacity() != null)
            sb.append("(최소 ").append(request.getCapacity()).append("명, ");

        if (Boolean.TRUE.equals(request.getRequireVideo()))
            sb.append("화상회의 지원, ");

        if (request.getBuilding() != null)
            sb.append(request.getBuilding()).append(" 건물");

        String msg = sb.toString().trim();
        if (msg.endsWith(",")) msg = msg.substring(0, msg.length() - 1);
        if (!msg.endsWith(")")) msg += ")";

        return msg;
    }
}
