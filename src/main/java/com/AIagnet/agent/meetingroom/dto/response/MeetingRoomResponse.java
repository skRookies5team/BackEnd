package com.AIagnet.agent.meetingroom.dto.response;

import com.AIagnet.agent.meetingroom.entity.MeetingRoom;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomResponse {

    /** 회의실 ID (Integer) */
    private Integer id;

    private String roomCode;

    private String name;

    private String building;

    private String floor;

    private Integer capacity;

    private Boolean hasVideo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /** 엔티티 → DTO 변환 */
    public static MeetingRoomResponse from(MeetingRoom meetingRoom) {
        return MeetingRoomResponse.builder()
                .id(meetingRoom.getId())
                .roomCode(meetingRoom.getRoomCode())
                .name(meetingRoom.getName())
                .building(meetingRoom.getBuilding())
                .floor(meetingRoom.getFloor())
                .capacity(meetingRoom.getCapacity())
                .hasVideo(meetingRoom.getHasVideo())
                .createdAt(meetingRoom.getCreatedAt())
                .updatedAt(meetingRoom.getUpdatedAt())
                .build();
    }
}
