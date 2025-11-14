package com.AIagnet.agent.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 정의
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common (1000번대)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "허용되지 않은 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 오류가 발생했습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "잘못된 타입입니다."),

    // Employee (2000번대)
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "E001", "직원을 찾을 수 없습니다."),
    EMPLOYEE_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "E002", "이미 존재하는 사번입니다."),
    EMPLOYEE_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "E003", "이미 존재하는 이메일입니다."),

    // MeetingRoom (3000번대)
    MEETING_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "회의실을 찾을 수 없습니다."),
    MEETING_ROOM_NOT_AVAILABLE(HttpStatus.CONFLICT, "M002", "해당 시간대에 예약 가능한 회의실이 없습니다."),

    // Reservation (4000번대)
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "예약을 찾을 수 없습니다."),
    RESERVATION_TIME_INVALID(HttpStatus.BAD_REQUEST, "R002", "시작 시간은 종료 시간보다 빨라야 합니다."),
    RESERVATION_TIME_PAST(HttpStatus.BAD_REQUEST, "R003", "과거 시간으로 예약할 수 없습니다."),
    RESERVATION_OVERLAPPING(HttpStatus.CONFLICT, "R004", "이미 해당 시간대에 예약이 존재합니다."),
    RESERVATION_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "R005", "참석 인원이 회의실 수용 인원을 초과합니다."),
    RESERVATION_ALREADY_CANCELLED(HttpStatus.CONFLICT, "R006", "이미 취소된 예약입니다."),
    RESERVATION_CANCELLED_NOT_MODIFIABLE(HttpStatus.CONFLICT, "R007", "취소된 예약은 수정할 수 없습니다."),

    // AI Agent (5000번대)
    AI_AGENT_CONNECTION_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "A001", "AI 에이전트 서버에 연결할 수 없습니다."),
    AI_AGENT_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "A002", "AI 에이전트 응답 처리 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}