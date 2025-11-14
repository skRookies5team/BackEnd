package com.AIagnet.agent.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 에러 응답 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    /**
     * 에러 코드
     */
    private String code;

    /**
     * 에러 메시지
     */
    private String message;

    /**
     * HTTP 상태 코드
     */
    private int status;

    /**
     * 발생 시각
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * 필드 에러 목록 (Validation 실패 시)
     */
    @Builder.Default
    private List<FieldError> errors = new ArrayList<>();

    /**
     * 필드 에러 정보
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }

    /**
     * ErrorCode로부터 ErrorResponse 생성
     */
    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .build();
    }

    /**
     * ErrorCode와 커스텀 메시지로 ErrorResponse 생성
     */
    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .status(errorCode.getHttpStatus().value())
                .build();
    }
}