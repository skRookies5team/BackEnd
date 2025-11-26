package com.AIagnet.agent.schedule.controller;

import com.AIagnet.agent.schedule.dto.ScheduleChatRequest;
import com.AIagnet.agent.schedule.dto.ScheduleChatResponse;
import com.AIagnet.agent.schedule.service.ScheduleProxyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 일정 챗봇 컨트롤러 테스트.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("일정 챗봇 API 테스트")
class ScheduleChatTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScheduleProxyService scheduleProxyService;

    @Test
    @DisplayName("일정 챗봇 요청 성공")
    void testScheduleChatSuccess() throws Exception {
        // Given
        ScheduleChatRequest request = ScheduleChatRequest.builder()
                .query("오늘 일정 알려줘")
                .employeeId(1)
                .build();

        ScheduleChatResponse mockResponse = ScheduleChatResponse.builder()
                .answer("오늘 일정은 다음과 같습니다...")
                .agent("schedule")
                .build();

        when(scheduleProxyService.chat(any(ScheduleChatRequest.class)))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/schedule/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").exists())
                .andExpect(jsonPath("$.agent").value("schedule"));
    }

    @Test
    @DisplayName("일정 챗봇 요청 - query 필수 검증")
    void testScheduleChatValidation() throws Exception {
        // Given
        ScheduleChatRequest request = ScheduleChatRequest.builder()
                .employeeId(1)
                // query 없음
                .build();

        // When & Then
        mockMvc.perform(post("/api/schedule/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

