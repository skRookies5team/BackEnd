package com.AIagnet.agent.mobiledevice.service;

import com.AIagnet.agent.mobiledevice.dto.response.MobileDeviceResponse;
import com.AIagnet.agent.mobiledevice.entity.MobileDevice;
import com.AIagnet.agent.mobiledevice.repository.MobileDeviceRepository;
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
 * 모바일 기기 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MobileDeviceService {

    private final MobileDeviceRepository mobileDeviceRepository;

    /**
     * 모바일 기기 목록 조회 (검색).
     */
    public Page<MobileDeviceResponse> searchMobileDevices(
            String companyName, String modelName, Integer releaseYear, Integer page, Integer size
    ) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20
        );

        Page<MobileDevice> devices = mobileDeviceRepository.search(
                companyName, modelName, releaseYear, pageable
        );

        return devices.map(MobileDeviceResponse::from);
    }

    /**
     * 모바일 기기 상세 조회.
     */
    public MobileDeviceResponse getMobileDeviceById(Integer productId) {
        MobileDevice device = mobileDeviceRepository.findById(productId)
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.INVALID_INPUT_VALUE,
                        "모바일 기기를 찾을 수 없습니다."
                ));
        return MobileDeviceResponse.from(device);
    }

    /**
     * 회사명별 조회.
     */
    public List<MobileDeviceResponse> getMobileDevicesByCompany(String companyName) {
        return mobileDeviceRepository.findByCompanyName(companyName).stream()
                .map(MobileDeviceResponse::from)
                .collect(Collectors.toList());
    }
}

