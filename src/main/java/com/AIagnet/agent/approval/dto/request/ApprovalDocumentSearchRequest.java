package com.AIagnet.agent.approval.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 결재 문서 검색 요청.
 */
@Getter
@Builder
@ToString
public class ApprovalDocumentSearchRequest {
    private Integer employeeId;
    private String status;
    private Integer currentApproverId;
    private Integer page;
    private Integer size;
}

