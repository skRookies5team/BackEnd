package com.AIagnet.agent.businesstrip.controller;

import com.AIagnet.agent.businesstrip.dto.BusinessTripChatRequest;
import com.AIagnet.agent.businesstrip.dto.BusinessTripChatResponse;
import com.AIagnet.agent.businesstrip.service.BusinessTripProxyService;
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
 * 출장 챗봇 컨트롤러 테스트.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("출장 챗봇 API 테스트")
class BusinessTripChatTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BusinessTripProxyService businessTripProxyService;

    @Test
    @DisplayName("출장 챗봇 요청 성공")
    void testBusinessTripChatSuccess() throws Exception {
        // Given
        BusinessTripChatRequest request = BusinessTripChatRequest.builder()
                .query("출장 일정 조회")
                .employeeId(1)
                .build();

        BusinessTripChatResponse mockResponse = BusinessTripChatResponse.builder()
                .answer("출장 일정은 다음과 같습니다...")
                .agent("business-trip")
                .build();

        when(businessTripProxyService.chat(any(BusinessTripChatRequest.class)))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/business-trips/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").exists())
                .andExpect(jsonPath("$.agent").value("business-trip"));
    }
}

