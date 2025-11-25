package com.AIagnet.agent.sales.controller;

import com.AIagnet.agent.sales.dto.request.CompanySalesSearchRequest;
import com.AIagnet.agent.sales.dto.response.CompanySalesResponse;
import com.AIagnet.agent.sales.service.CompanySalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 회사 매출 컨트롤러.
 */
@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Slf4j
public class CompanySalesController {

    private final CompanySalesService companySalesService;

    /**
     * 매출 목록 조회 (검색).
     */
    @GetMapping
    public ResponseEntity<Page<CompanySalesResponse>> searchCompanySales(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String productCategory,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String country,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        log.info("GET /api/sales - startDate={}, endDate={}, productCategory={}, region={}, country={}, page={}, size={}",
                startDate, endDate, productCategory, region, country, page, size);

        CompanySalesSearchRequest request = CompanySalesSearchRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .productCategory(productCategory)
                .region(region)
                .country(country)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(companySalesService.searchCompanySales(request));
    }

    /**
     * 매출 상세 조회.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanySalesResponse> getCompanySalesById(@PathVariable Long id) {
        log.info("GET /api/sales/{}", id);
        return ResponseEntity.ok(companySalesService.getCompanySalesById(id));
    }

    /**
     * 제품 카테고리별 매출 조회.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CompanySalesResponse>> getCompanySalesByCategory(
            @PathVariable String category
    ) {
        log.info("GET /api/sales/category/{}", category);
        return ResponseEntity.ok(companySalesService.getCompanySalesByCategory(category));
    }

    /**
     * 지역별 매출 조회.
     */
    @GetMapping("/region/{region}")
    public ResponseEntity<List<CompanySalesResponse>> getCompanySalesByRegion(
            @PathVariable String region
    ) {
        log.info("GET /api/sales/region/{}", region);
        return ResponseEntity.ok(companySalesService.getCompanySalesByRegion(region));
    }
}

