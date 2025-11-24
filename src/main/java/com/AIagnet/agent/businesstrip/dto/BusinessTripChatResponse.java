package com.AIagnet.agent.businesstrip.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 출장 챗봇 응답 DTO.
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessTripChatResponse {
    @JsonProperty("ok")
    private boolean success;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("rows")
    private List<Map<String, Object>> rows;
    
    @JsonProperty("error")
    private String error;
}
