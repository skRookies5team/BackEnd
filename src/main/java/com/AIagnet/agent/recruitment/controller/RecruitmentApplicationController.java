package com.AIagnet.agent.recruitment.controller;

import com.AIagnet.agent.recruitment.dto.request.RecruitmentApplicationSearchRequest;
import com.AIagnet.agent.recruitment.dto.response.RecruitmentApplicationResponse;
import com.AIagnet.agent.recruitment.service.RecruitmentApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 채용 지원서 컨트롤러.
 */
@RestController
@RequestMapping("/api/recruitment/applications")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "채용 관리", description = "채용 지원서 조회 API")
public class RecruitmentApplicationController {

    private final RecruitmentApplicationService recruitmentApplicationService;

    /**
     * 채용 지원서 목록 조회 (검색).
     */
    @GetMapping
    public ResponseEntity<Page<RecruitmentApplicationResponse>> searchRecruitmentApplications(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String appliedPosition,
            @RequestParam(required = false) String applicationStatus,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        log.info("GET /api/recruitment/applications - startDate={}, endDate={}, appliedPosition={}, applicationStatus={}, page={}, size={}",
                startDate, endDate, appliedPosition, applicationStatus, page, size);

        RecruitmentApplicationSearchRequest request = RecruitmentApplicationSearchRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .appliedPosition(appliedPosition)
                .applicationStatus(applicationStatus)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(recruitmentApplicationService.searchRecruitmentApplications(request));
    }

    /**
     * 채용 지원서 상세 조회.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentApplicationResponse> getRecruitmentApplicationById(
            @PathVariable Long id
    ) {
        log.info("GET /api/recruitment/applications/{}", id);
        return ResponseEntity.ok(recruitmentApplicationService.getRecruitmentApplicationById(id));
    }

    /**
     * 지원 상태별 조회.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RecruitmentApplicationResponse>> getRecruitmentApplicationsByStatus(
            @PathVariable String status
    ) {
        log.info("GET /api/recruitment/applications/status/{}", status);
        return ResponseEntity.ok(recruitmentApplicationService.getRecruitmentApplicationsByStatus(status));
    }

    /**
     * 지원 직무별 조회.
     */
    @GetMapping("/position/{position}")
    public ResponseEntity<List<RecruitmentApplicationResponse>> getRecruitmentApplicationsByPosition(
            @PathVariable String position
    ) {
        log.info("GET /api/recruitment/applications/position/{}", position);
        return ResponseEntity.ok(recruitmentApplicationService.getRecruitmentApplicationsByPosition(position));
    }
}

