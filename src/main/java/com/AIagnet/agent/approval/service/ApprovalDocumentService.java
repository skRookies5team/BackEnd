package com.AIagnet.agent.approval.service;

import com.AIagnet.agent.approval.dto.request.ApprovalDocumentCreateRequest;
import com.AIagnet.agent.approval.dto.request.ApprovalDocumentSearchRequest;
import com.AIagnet.agent.approval.dto.response.ApprovalDocumentResponse;
import com.AIagnet.agent.approval.entity.ApprovalDocument;
import com.AIagnet.agent.approval.repository.ApprovalDocumentRepository;
import com.AIagnet.agent.common.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 결재 문서 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ApprovalDocumentService {

    private final ApprovalDocumentRepository approvalDocumentRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * 결재 문서 목록 조회.
     */
    public Page<ApprovalDocumentResponse> searchApprovalDocuments(ApprovalDocumentSearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() != null ? request.getPage() : 0,
                request.getSize() != null ? request.getSize() : 20
        );

        Page<ApprovalDocument> documents;
        if (request.getStatus() != null) {
            documents = approvalDocumentRepository.findByStatus(request.getStatus(), pageable);
        } else if (request.getEmployeeId() != null) {
            List<ApprovalDocument> list = approvalDocumentRepository.findByEmployeeId(request.getEmployeeId());
            // List를 Page로 변환 (간단한 구현)
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), list.size());
            List<ApprovalDocument> pageContent = list.subList(start, end);
            documents = new org.springframework.data.domain.PageImpl<>(pageContent, pageable, list.size());
        } else if (request.getCurrentApproverId() != null) {
            List<ApprovalDocument> list = approvalDocumentRepository.findByCurrentApproverId(request.getCurrentApproverId());
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), list.size());
            List<ApprovalDocument> pageContent = list.subList(start, end);
            documents = new org.springframework.data.domain.PageImpl<>(pageContent, pageable, list.size());
        } else {
            documents = approvalDocumentRepository.findAll(pageable);
        }

        return documents.map(ApprovalDocumentResponse::from);
    }

    /**
     * 결재 문서 상세 조회.
     */
    public ApprovalDocumentResponse getApprovalDocumentById(Integer documentId) {
        ApprovalDocument document = approvalDocumentRepository.findByIdWithRelations(documentId)
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.INVALID_INPUT_VALUE,
                        "결재 문서를 찾을 수 없습니다."
                ));
        return ApprovalDocumentResponse.from(document);
    }

    /**
     * 결재 문서 생성.
     */
    @Transactional
    public ApprovalDocumentResponse createApprovalDocument(ApprovalDocumentCreateRequest request) {
        var employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.EMPLOYEE_NOT_FOUND,
                        "직원을 찾을 수 없습니다."
                ));

        ApprovalDocument document = ApprovalDocument.builder()
                .employee(employee)
                .documentType(request.getDocumentType())
                .title(request.getTitle())
                .content(request.getContent())
                .status("대기")
                .build();

        ApprovalDocument saved = approvalDocumentRepository.save(document);
        log.info("결재 문서 생성 완료: documentId={}", saved.getDocumentId());
        return ApprovalDocumentResponse.from(saved);
    }
}

