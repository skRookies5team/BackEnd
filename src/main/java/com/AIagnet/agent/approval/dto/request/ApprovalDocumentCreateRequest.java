package com.AIagnet.agent.approval.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 결재 문서 생성 요청.
 */
@Getter
@Builder
@ToString
public class ApprovalDocumentCreateRequest {
    @NotNull(message = "직원 ID는 필수입니다.")
    private Integer employeeId;

    @NotBlank(message = "문서 유형은 필수입니다.")
    private String documentType;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String content;
}

