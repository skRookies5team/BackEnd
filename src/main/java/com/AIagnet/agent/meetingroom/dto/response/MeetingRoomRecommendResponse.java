package com.AIagnet.agent.meetingroom.dto.response;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomRecommendResponse {

    private List<MeetingRoomResponse> recommendedRooms;

    private String message;

    private Integer totalCount;
}
