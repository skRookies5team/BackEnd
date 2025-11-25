package com.AIagnet.agent.recruitment.service;

import com.AIagnet.agent.recruitment.dto.request.RecruitmentApplicationSearchRequest;
import com.AIagnet.agent.recruitment.dto.response.RecruitmentApplicationResponse;
import com.AIagnet.agent.recruitment.entity.RecruitmentApplication;
import com.AIagnet.agent.recruitment.repository.RecruitmentApplicationRepository;
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
 * 채용 지원서 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RecruitmentApplicationService {

    private final RecruitmentApplicationRepository recruitmentApplicationRepository;

    /**
     * 채용 지원서 목록 조회 (검색).
     */
    public Page<RecruitmentApplicationResponse> searchRecruitmentApplications(
            RecruitmentApplicationSearchRequest request
    ) {
        Pageable pageable = PageRequest.of(
                request.getPage() != null ? request.getPage() : 0,
                request.getSize() != null ? request.getSize() : 20
        );

        Page<RecruitmentApplication> applications = recruitmentApplicationRepository.search(
                request.getStartDate(),
                request.getEndDate(),
                request.getAppliedPosition(),
                request.getApplicationStatus(),
                pageable
        );

        return applications.map(RecruitmentApplicationResponse::from);
    }

    /**
     * 채용 지원서 상세 조회.
     */
    public RecruitmentApplicationResponse getRecruitmentApplicationById(Long id) {
        RecruitmentApplication application = recruitmentApplicationRepository.findById(id)
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.INVALID_INPUT_VALUE,
                        "채용 지원서를 찾을 수 없습니다."
                ));
        return RecruitmentApplicationResponse.from(application);
    }

    /**
     * 지원 상태별 조회.
     */
    public List<RecruitmentApplicationResponse> getRecruitmentApplicationsByStatus(String status) {
        return recruitmentApplicationRepository.findByApplicationStatus(status).stream()
                .map(RecruitmentApplicationResponse::from)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 지원 직무별 조회.
     */
    public List<RecruitmentApplicationResponse> getRecruitmentApplicationsByPosition(String position) {
        return recruitmentApplicationRepository.findByAppliedPosition(position).stream()
                .map(RecruitmentApplicationResponse::from)
                .collect(java.util.stream.Collectors.toList());
    }
}

