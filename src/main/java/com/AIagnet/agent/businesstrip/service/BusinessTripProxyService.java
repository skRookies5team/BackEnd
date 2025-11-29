package com.AIagnet.agent.businesstrip.service;

import com.AIagnet.agent.businesstrip.dto.BusinessTripChatRequest;
import com.AIagnet.agent.businesstrip.dto.BusinessTripChatResponse;
import com.AIagnet.agent.common.service.BaseFastApiProxyService;
import com.AIagnet.agent.common.service.FastApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * 출장 챗봇 프록시 서비스 (FastAPI 연동).
 */
@Service
public class BusinessTripProxyService extends BaseFastApiProxyService<BusinessTripChatRequest, BusinessTripChatResponse> {

    public BusinessTripProxyService(FastApiClient fastApiClient, ObjectMapper objectMapper) {
        super(fastApiClient, objectMapper);
    }

    @Override
    protected String getAgentType() {
        return "business-trip";
    }

    @Override
    protected Class<BusinessTripChatResponse> getResponseClass() {
        return BusinessTripChatResponse.class;
    }
}

