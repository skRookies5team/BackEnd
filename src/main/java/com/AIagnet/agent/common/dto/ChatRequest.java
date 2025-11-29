package com.AIagnet.agent.common.dto;

/**
 * 챗봇 요청 공통 인터페이스.
 * 모든 챗봇 요청 DTO가 구현해야 하는 인터페이스입니다.
 */
public interface ChatRequest {

    /**
     * 사용자 질문을 반환합니다.
     *
     * @return 질문 텍스트
     */
    String getQuery();

    /**
     * 직원 ID를 반환합니다.
     *
     * @return 직원 ID (nullable)
     */
    Integer getEmployeeId();
}

