package com.AIagnet.agent.common.dto;

/**
 * 챗봇 응답 공통 인터페이스.
 * 모든 챗봇 응답 DTO가 구현해야 하는 인터페이스입니다.
 */
public interface ChatResponse {

    /**
     * 챗봇 응답 텍스트를 반환합니다.
     *
     * @return 응답 텍스트
     */
    String getAnswer();
}

