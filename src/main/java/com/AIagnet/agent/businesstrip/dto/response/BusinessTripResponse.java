package com.AIagnet.agent.businesstrip.dto.response;

import com.AIagnet.agent.businesstrip.entity.BusinessTrip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 출장 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessTripResponse {
    private Integer tripId;
    private Integer employeeId;
    private String employeeName;
    private String tripTitle;
    private String tripPurpose;
    private String destination;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime alertTime;

    public static BusinessTripResponse from(BusinessTrip businessTrip) {
        return BusinessTripResponse.builder()
                .tripId(businessTrip.getTripId())
                .employeeId(businessTrip.getEmployee().getEmployeeId())
                .employeeName(businessTrip.getEmployee().getName())
                .tripTitle(businessTrip.getTripTitle())
                .tripPurpose(businessTrip.getTripPurpose())
                .destination(businessTrip.getDestination())
                .startTime(businessTrip.getStartTime())
                .endTime(businessTrip.getEndTime())
                .createdAt(businessTrip.getCreatedAt())
                .alertTime(businessTrip.getAlertTime())
                .build();
    }
}

