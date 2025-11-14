package com.AIagnet.agent.meetingroom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 예약 취소 요청 DTO (AI-009)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCancelRequest {

    /**
     * 취소 사유
     */
    private String reason;
}