package com.AIagnet.agent.approval.dto.response;

import com.AIagnet.agent.approval.entity.ApprovalDocument;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 결재 문서 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApprovalDocumentResponse {
    private Integer documentId;
    private Integer employeeId;
    private String employeeName;
    private String documentType;
    private String title;
    private String content;
    private String status;
    private Integer currentApproverId;
    private String currentApproverName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ApprovalDocumentResponse from(ApprovalDocument document) {
        return ApprovalDocumentResponse.builder()
                .documentId(document.getDocumentId())
                .employeeId(document.getEmployee().getEmployeeId())
                .employeeName(document.getEmployee().getName())
                .documentType(document.getDocumentType())
                .title(document.getTitle())
                .content(document.getContent())
                .status(document.getStatus())
                .currentApproverId(document.getCurrentApprover() != null ? document.getCurrentApprover().getEmployeeId() : null)
                .currentApproverName(document.getCurrentApprover() != null ? document.getCurrentApprover().getName() : null)
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }
}

