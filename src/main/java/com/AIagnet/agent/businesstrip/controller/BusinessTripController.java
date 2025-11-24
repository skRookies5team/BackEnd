package com.AIagnet.agent.businesstrip.controller;

import com.AIagnet.agent.businesstrip.dto.BusinessTripChatRequest;
import com.AIagnet.agent.businesstrip.dto.BusinessTripChatResponse;
import com.AIagnet.agent.businesstrip.dto.request.BusinessTripCreateRequest;
import com.AIagnet.agent.businesstrip.dto.request.BusinessTripSearchRequest;
import com.AIagnet.agent.businesstrip.dto.response.BusinessTripResponse;
import com.AIagnet.agent.businesstrip.service.BusinessTripProxyService;
import com.AIagnet.agent.businesstrip.service.BusinessTripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 출장 관리 컨트롤러.
 */
@RestController
@RequestMapping("/api/business-trips")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BusinessTripController {

    private final BusinessTripProxyService businessTripProxyService;
    private final BusinessTripService businessTripService;

    /**
     * 출장 챗봇 POST 엔드포인트 (FastAPI 프록시).
     *
     * @param request 출장 질의 요청
     * @return FastAPI 응답
     */
    @PostMapping("/chat")
    public ResponseEntity<BusinessTripChatResponse> chat(@Valid @RequestBody BusinessTripChatRequest request) {
        log.info("POST /api/business-trips/chat - query={}", request.getQuery());
        BusinessTripChatResponse response = businessTripProxyService.queryBusinessTrip(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 출장 챗봇 GET 엔드포인트 (질의 파라미터 버전).
     *
     * @param query 질의 문장 (필수)
     * @return FastAPI 응답
     */
    @GetMapping("/chat")
    public ResponseEntity<BusinessTripChatResponse> getBusinessTripChat(@RequestParam("query") String query) {
        log.info("GET /api/business-trips/chat - query={}", query);
        BusinessTripChatRequest request = BusinessTripChatRequest.builder()
                .query(query)
                .build();
        BusinessTripChatResponse response = businessTripProxyService.queryBusinessTrip(request);
        return ResponseEntity.ok(response);
    }

    // ========== 출장 관리 CRUD 엔드포인트 ==========

    /**
     * 출장 목록 조회.
     */
    @GetMapping
    public ResponseEntity<List<BusinessTripResponse>> getBusinessTrips(
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        log.info("GET /api/business-trips - employeeId={}, startTime={}, endTime={}",
                employeeId, startTime, endTime);
        BusinessTripSearchRequest request = BusinessTripSearchRequest.builder()
                .employeeId(employeeId)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        return ResponseEntity.ok(businessTripService.getBusinessTrips(request));
    }

    /**
     * 출장 상세 조회.
     */
    @GetMapping("/{tripId}")
    public ResponseEntity<BusinessTripResponse> getBusinessTripById(@PathVariable Integer tripId) {
        log.info("GET /api/business-trips/{}", tripId);
        return ResponseEntity.ok(businessTripService.getBusinessTripById(tripId));
    }

    /**
     * 출장 등록.
     */
    @PostMapping
    public ResponseEntity<BusinessTripResponse> createBusinessTrip(
            @Valid @RequestBody BusinessTripCreateRequest request
    ) {
        log.info("POST /api/business-trips - employeeId={}, tripTitle={}",
                request.getEmployeeId(), request.getTripTitle());
        return ResponseEntity.ok(businessTripService.createBusinessTrip(request));
    }

    /**
     * 출장 삭제.
     */
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteBusinessTrip(@PathVariable Integer tripId) {
        log.info("DELETE /api/business-trips/{}", tripId);
        businessTripService.deleteBusinessTrip(tripId);
        return ResponseEntity.noContent().build();
    }
}
