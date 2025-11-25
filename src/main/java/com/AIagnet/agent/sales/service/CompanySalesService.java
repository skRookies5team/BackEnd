package com.AIagnet.agent.sales.service;

import com.AIagnet.agent.sales.dto.request.CompanySalesSearchRequest;
import com.AIagnet.agent.sales.dto.response.CompanySalesResponse;
import com.AIagnet.agent.sales.repository.CompanySalesRepository;
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
 * 회사 매출 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CompanySalesService {

    private final CompanySalesRepository companySalesRepository;

    /**
     * 매출 목록 조회 (검색).
     */
    public Page<CompanySalesResponse> searchCompanySales(CompanySalesSearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() != null ? request.getPage() : 0,
                request.getSize() != null ? request.getSize() : 20
        );

        Page<com.AIagnet.agent.sales.entity.CompanySales> salesPage = companySalesRepository.search(
                request.getStartDate(),
                request.getEndDate(),
                request.getProductCategory(),
                request.getRegion(),
                request.getCountry(),
                pageable
        );

        return salesPage.map(CompanySalesResponse::from);
    }

    /**
     * 매출 상세 조회.
     */
    public CompanySalesResponse getCompanySalesById(Long id) {
        com.AIagnet.agent.sales.entity.CompanySales sales = companySalesRepository.findById(id)
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.INVALID_INPUT_VALUE,
                        "매출 정보를 찾을 수 없습니다."
                ));
        return CompanySalesResponse.from(sales);
    }

    /**
     * 제품 카테고리별 매출 조회.
     */
    public List<CompanySalesResponse> getCompanySalesByCategory(String category) {
        return companySalesRepository.findByProductCategory(category).stream()
                .map(CompanySalesResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 지역별 매출 조회.
     */
    public List<CompanySalesResponse> getCompanySalesByRegion(String region) {
        return companySalesRepository.findByRegion(region).stream()
                .map(CompanySalesResponse::from)
                .collect(Collectors.toList());
    }
}

