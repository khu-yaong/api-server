package com.khu.yaong.domain.auth.exception;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum AuthSuccessCode implements SuccessResponse {
    REGISTER_SUCCESS(HttpStatus.CREATED, "AUTH200", "회원가입이 성공적으로 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH201", "로그인이 성공적으로 완료되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "AUTH202", "로그아웃이 성공적으로 완료되었습니다."),
    TOKEN_REFRESH_SUCCESS(HttpStatus.CREATED,"AUTH203","액세스 토큰이 성공적으로 재발급되었습니다."),

    // 이메일 관련 성공코드
    SEND_EMAIL_SUCCESS(HttpStatus.OK, "AUTH210", "이메일 인증 코드가 성공적으로 발송되었습니다."),
    VERIFY_EMAIL_SUCCESS(HttpStatus.OK, "AUTH211", "이메일 인증이 성공적으로 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthSuccessCode(HttpStatus httpStatus, String code, String message) {
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

