package com.AIagnet.agent.costing.controller;

import com.AIagnet.agent.costing.dto.response.ActivityBasedCostingResponse;
import com.AIagnet.agent.costing.service.ActivityBasedCostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 활동기준원가 컨트롤러.
 */
@RestController
@RequestMapping("/api/costing/activities")
@RequiredArgsConstructor
@Slf4j
public class ActivityBasedCostingController {

    private final ActivityBasedCostingService activityBasedCostingService;

    /**
     * 활동기준원가 목록 조회 (검색).
     */
    @GetMapping
    public ResponseEntity<Page<ActivityBasedCostingResponse>> searchActivityBasedCosting(
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String productivityImpactLevel,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        log.info("GET /api/costing/activities - activityType={}, department={}, productivityImpactLevel={}, startDate={}, endDate={}, page={}, size={}",
                activityType, department, productivityImpactLevel, startDate, endDate, page, size);
        return ResponseEntity.ok(activityBasedCostingService.searchActivityBasedCosting(
                activityType, department, productivityImpactLevel, startDate, endDate, page, size));
    }

    /**
     * 활동기준원가 상세 조회.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActivityBasedCostingResponse> getActivityBasedCostingById(@PathVariable Long id) {
        log.info("GET /api/costing/activities/{}", id);
        return ResponseEntity.ok(activityBasedCostingService.getActivityBasedCostingById(id));
    }

    /**
     * 부서별 조회.
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<ActivityBasedCostingResponse>> getActivityBasedCostingByDepartment(
            @PathVariable String department
    ) {
        log.info("GET /api/costing/activities/department/{}", department);
        return ResponseEntity.ok(activityBasedCostingService.getActivityBasedCostingByDepartment(department));
    }
}

