package com.AIagnet.agent.sales.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 회사 매출 검색 요청.
 */
@Getter
@Builder
@ToString
public class CompanySalesSearchRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String productCategory;
    private String region;
    private String country;
    private Integer page;
    private Integer size;
}

