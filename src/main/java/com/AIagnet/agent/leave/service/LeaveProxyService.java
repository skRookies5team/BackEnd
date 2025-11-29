package com.AIagnet.agent.leave.service;

import com.AIagnet.agent.common.service.BaseFastApiProxyService;
import com.AIagnet.agent.common.service.FastApiClient;
import com.AIagnet.agent.leave.dto.LeaveChatRequest;
import com.AIagnet.agent.leave.dto.LeaveChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * 연차 챗봇 프록시 서비스 (FastAPI 연동).
 */
@Service
public class LeaveProxyService extends BaseFastApiProxyService<LeaveChatRequest, LeaveChatResponse> {

    public LeaveProxyService(FastApiClient fastApiClient, ObjectMapper objectMapper) {
        super(fastApiClient, objectMapper);
    }

    @Override
    protected String getAgentType() {
        return "leave";
    }

    @Override
    protected Class<LeaveChatResponse> getResponseClass() {
        return LeaveChatResponse.class;
    }
}

