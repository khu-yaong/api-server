package com.khu.yaong.global.common.response.member;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements SuccessResponse {
    REGISTER_SUCCESS(HttpStatus.CREATED,"MEMBER_CREATED", "회원가입이 성공적으로 이루어졌습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,"LOGIN_SUCCESS", "로그인이 성공적으로 이루어졌습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MemberSuccessCode(HttpStatus httpStatus, String code, String message) {
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

