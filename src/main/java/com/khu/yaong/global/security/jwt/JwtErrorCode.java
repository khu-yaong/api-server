package com.khu.yaong.global.security.jwt;

import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;


public enum JwtErrorCode implements ErrorResponse {
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT401", "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "JWT402", "잘못된 형식의 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "JWT403", "지원되지 않는 형식의 토큰입니다."),
    WEAK_SIGNATURE(HttpStatus.BAD_REQUEST, "JWT404", "서명 키가 약해서 토큰이 유효하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT405", "요청에 토큰이 포함되어 있지 않습니다."),
    TOKEN_PARSE_ERROR(HttpStatus.BAD_REQUEST, "JWT406", "토큰을 파싱하는 중 오류가 발생했습니다."),

    AUTHENTICATION_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT407", "인증 정보를 찾을 수 없습니다."),
    UNKNOWN_PRINCIPAL_TYPE(HttpStatus.UNAUTHORIZED, "JWT408", "알 수 없는 사용자 타입입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    JwtErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

