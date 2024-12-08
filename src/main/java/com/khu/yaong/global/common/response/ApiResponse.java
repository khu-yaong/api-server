package com.khu.yaong.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.khu.yaong.global.common.type.ErrorResponse;
import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;
import java.time.LocalDateTime;


@JsonPropertyOrder({"status", "code", "message", "data", "timestamp"})
public record ApiResponse<T>(
        int status,           // HTTP 상태 코드
        //HttpStatus status,
        String code,          // 애플리케이션 정의 코드
        String message,       // 응답 메시지
        @JsonInclude(JsonInclude.Include.NON_NULL) T data,   // 응답 데이터
        LocalDateTime timestamp  // 응답 생성 시각
) {
    public static <T> ApiResponse<T> success(SuccessResponse successResponse, T data) {
        return new ApiResponse<>(successResponse.getHttpStatus().value(),successResponse.getCode(), successResponse.getMessage(), data, LocalDateTime.now());
    }
    public static ApiResponse<Void> success(SuccessResponse successResponse) {
        return new ApiResponse<>(successResponse.getHttpStatus().value(),successResponse.getCode(), successResponse.getMessage(),null, LocalDateTime.now());
    }


    public static ApiResponse<?> error(ErrorResponse errorResponse) {
        return new ApiResponse<>(
                errorResponse.getHttpStatus().value(),  // HTTP 상태 코드
                errorResponse.getCode(),                // 애플리케이션 정의 코드
                errorResponse.getMessage(),             // 응답 메세지 (에러 메세지)
                null,
                LocalDateTime.now());
    }



}