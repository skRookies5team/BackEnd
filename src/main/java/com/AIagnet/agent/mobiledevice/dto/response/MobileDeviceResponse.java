package com.AIagnet.agent.mobiledevice.dto.response;

import com.AIagnet.agent.mobiledevice.entity.MobileDevice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 모바일 기기 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MobileDeviceResponse {
    private Integer productId;
    private String companyName;
    private String modelName;
    private String weight;
    private String ram;
    private String frontCamera;
    private String rearCamera;
    private String processor;
    private String batteryCapacity;
    private String screenSize;
    private String launchPriceUsa;
    private String launchPriceKor;
    private Integer releaseYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MobileDeviceResponse from(MobileDevice device) {
        return MobileDeviceResponse.builder()
                .productId(device.getProductId())
                .companyName(device.getCompanyName())
                .modelName(device.getModelName())
                .weight(device.getWeight())
                .ram(device.getRam())
                .frontCamera(device.getFrontCamera())
                .rearCamera(device.getRearCamera())
                .processor(device.getProcessor())
                .batteryCapacity(device.getBatteryCapacity())
                .screenSize(device.getScreenSize())
                .launchPriceUsa(device.getLaunchPriceUsa())
                .launchPriceKor(device.getLaunchPriceKor())
                .releaseYear(device.getReleaseYear())
                .createdAt(device.getCreatedAt())
                .updatedAt(device.getUpdatedAt())
                .build();
    }
}

