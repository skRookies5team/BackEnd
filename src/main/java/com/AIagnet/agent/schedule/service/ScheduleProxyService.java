package com.AIagnet.agent.schedule.service;

import com.AIagnet.agent.common.service.BaseFastApiProxyService;
import com.AIagnet.agent.common.service.FastApiClient;
import com.AIagnet.agent.schedule.dto.ScheduleChatRequest;
import com.AIagnet.agent.schedule.dto.ScheduleChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * 일정 챗봇 프록시 서비스 (FastAPI 연동).
 */
@Service
public class ScheduleProxyService extends BaseFastApiProxyService<ScheduleChatRequest, ScheduleChatResponse> {

    public ScheduleProxyService(FastApiClient fastApiClient, ObjectMapper objectMapper) {
        super(fastApiClient, objectMapper);
    }

    @Override
    protected String getAgentType() {
        return "schedule";
    }

    @Override
    protected Class<ScheduleChatResponse> getResponseClass() {
        return ScheduleChatResponse.class;
    }
}

