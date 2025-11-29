package com.AIagnet.agent.meetingroom.service;

import com.AIagnet.agent.common.service.BaseFastApiProxyService;
import com.AIagnet.agent.common.service.FastApiClient;
import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatRequest;
import com.AIagnet.agent.meetingroom.dto.MeetingRoomChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * 회의실 챗봇 프록시 서비스 (FastAPI 연동).
 */
@Service
public class MeetingRoomProxyService extends BaseFastApiProxyService<MeetingRoomChatRequest, MeetingRoomChatResponse> {

    public MeetingRoomProxyService(FastApiClient fastApiClient, ObjectMapper objectMapper) {
        super(fastApiClient, objectMapper);
    }

    @Override
    protected String getAgentType() {
        return "meeting-room";
    }

    @Override
    protected Class<MeetingRoomChatResponse> getResponseClass() {
        return MeetingRoomChatResponse.class;
    }
}

