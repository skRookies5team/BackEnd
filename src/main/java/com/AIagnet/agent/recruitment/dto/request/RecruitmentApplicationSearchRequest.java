package com.AIagnet.agent.recruitment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 채용 지원서 검색 요청.
 */
@Getter
@Builder
@ToString
public class RecruitmentApplicationSearchRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String appliedPosition;
    private String applicationStatus;
    private Integer page;
    private Integer size;
}

