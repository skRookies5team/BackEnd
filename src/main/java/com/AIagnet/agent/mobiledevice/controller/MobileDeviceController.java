package com.AIagnet.agent.mobiledevice.controller;

import com.AIagnet.agent.mobiledevice.dto.response.MobileDeviceResponse;
import com.AIagnet.agent.mobiledevice.service.MobileDeviceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 모바일 기기 컨트롤러.
 */
@RestController
@RequestMapping("/api/mobile-devices")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "모바일 기기", description = "모바일 기기 정보 조회 API")
public class MobileDeviceController {

    private final MobileDeviceService mobileDeviceService;

    /**
     * 모바일 기기 목록 조회 (검색).
     */
    @GetMapping
    public ResponseEntity<Page<MobileDeviceResponse>> searchMobileDevices(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        log.info("GET /api/mobile-devices - companyName={}, modelName={}, releaseYear={}, page={}, size={}",
                companyName, modelName, releaseYear, page, size);
        return ResponseEntity.ok(mobileDeviceService.searchMobileDevices(companyName, modelName, releaseYear, page, size));
    }

    /**
     * 모바일 기기 상세 조회.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<MobileDeviceResponse> getMobileDeviceById(@PathVariable Integer productId) {
        log.info("GET /api/mobile-devices/{}", productId);
        return ResponseEntity.ok(mobileDeviceService.getMobileDeviceById(productId));
    }

    /**
     * 회사명별 조회.
     */
    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<MobileDeviceResponse>> getMobileDevicesByCompany(
            @PathVariable String companyName
    ) {
        log.info("GET /api/mobile-devices/company/{}", companyName);
        return ResponseEntity.ok(mobileDeviceService.getMobileDevicesByCompany(companyName));
    }
}

