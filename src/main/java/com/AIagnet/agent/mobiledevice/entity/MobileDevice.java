package com.AIagnet.agent.mobiledevice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 모바일 기기 엔터티.
 */
@Entity
@Table(name = "mobile_devices")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MobileDevice {

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "model_name", nullable = false, length = 200)
    private String modelName;

    @Column(name = "weight", length = 50)
    private String weight;

    @Column(name = "ram", length = 50)
    private String ram;

    @Column(name = "front_camera", length = 200)
    private String frontCamera;

    @Column(name = "rear_camera", length = 300)
    private String rearCamera;

    @Column(name = "processor", length = 200)
    private String processor;

    @Column(name = "battery_capacity", length = 100)
    private String batteryCapacity;

    @Column(name = "screen_size", length = 100)
    private String screenSize;

    @Column(name = "launch_price_usa", length = 50)
    private String launchPriceUsa;

    @Column(name = "launch_price_kor", length = 50)
    private String launchPriceKor;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

