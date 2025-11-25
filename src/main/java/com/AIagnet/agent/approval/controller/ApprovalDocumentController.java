package com.AIagnet.agent.approval.controller;

import com.AIagnet.agent.approval.dto.request.ApprovalDocumentCreateRequest;
import com.AIagnet.agent.approval.dto.request.ApprovalDocumentSearchRequest;
import com.AIagnet.agent.approval.dto.response.ApprovalDocumentResponse;
import com.AIagnet.agent.approval.service.ApprovalDocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 결재 문서 컨트롤러.
 */
@RestController
@RequestMapping("/api/approval/documents")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "결재 관리", description = "결재 문서 조회, 생성 API")
public class ApprovalDocumentController {

    private final ApprovalDocumentService approvalDocumentService;

    /**
     * 결재 문서 목록 조회.
     */
    @GetMapping
    public ResponseEntity<Page<ApprovalDocumentResponse>> searchApprovalDocuments(
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer currentApproverId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        log.info("GET /api/approval/documents - employeeId={}, status={}, currentApproverId={}, page={}, size={}",
                employeeId, status, currentApproverId, page, size);

        ApprovalDocumentSearchRequest request = ApprovalDocumentSearchRequest.builder()
                .employeeId(employeeId)
                .status(status)
                .currentApproverId(currentApproverId)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(approvalDocumentService.searchApprovalDocuments(request));
    }

    /**
     * 결재 문서 상세 조회.
     */
    @GetMapping("/{documentId}")
    public ResponseEntity<ApprovalDocumentResponse> getApprovalDocumentById(
            @PathVariable Integer documentId
    ) {
        log.info("GET /api/approval/documents/{}", documentId);
        return ResponseEntity.ok(approvalDocumentService.getApprovalDocumentById(documentId));
    }

    /**
     * 결재 문서 생성.
     */
    @PostMapping
    public ResponseEntity<ApprovalDocumentResponse> createApprovalDocument(
            @Valid @RequestBody ApprovalDocumentCreateRequest request
    ) {
        log.info("POST /api/approval/documents - employeeId={}, title={}",
                request.getEmployeeId(), request.getTitle());
        return ResponseEntity.ok(approvalDocumentService.createApprovalDocument(request));
    }
}

