package com.AIagnet.agent.spend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 지출 거래 검색 요청.
 */
@Getter
@Builder
@ToString
public class SpendTransactionSearchRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    private String supplier;
    private String buyer;
    private Integer page;
    private Integer size;
}

