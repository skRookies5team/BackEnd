package com.AIagnet.agent.costing.service;

import com.AIagnet.agent.costing.dto.response.ActivityBasedCostingResponse;
import com.AIagnet.agent.costing.entity.ActivityBasedCosting;
import com.AIagnet.agent.costing.repository.ActivityBasedCostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 활동기준원가 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ActivityBasedCostingService {

    private final ActivityBasedCostingRepository activityBasedCostingRepository;

    /**
     * 활동기준원가 목록 조회 (검색).
     */
    public Page<ActivityBasedCostingResponse> searchActivityBasedCosting(
            String activityType, String department, String productivityImpactLevel,
            LocalDate startDate, LocalDate endDate, Integer page, Integer size
    ) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20
        );

        Page<ActivityBasedCosting> costings = activityBasedCostingRepository.search(
                activityType, department, productivityImpactLevel, startDate, endDate, pageable
        );

        return costings.map(ActivityBasedCostingResponse::from);
    }

    /**
     * 활동기준원가 상세 조회.
     */
    public ActivityBasedCostingResponse getActivityBasedCostingById(Long id) {
        ActivityBasedCosting costing = activityBasedCostingRepository.findById(id)
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.INVALID_INPUT_VALUE,
                        "활동기준원가를 찾을 수 없습니다."
                ));
        return ActivityBasedCostingResponse.from(costing);
    }

    /**
     * 부서별 조회.
     */
    public List<ActivityBasedCostingResponse> getActivityBasedCostingByDepartment(String department) {
        return activityBasedCostingRepository.findByDepartment(department).stream()
                .map(ActivityBasedCostingResponse::from)
                .collect(Collectors.toList());
    }
}

