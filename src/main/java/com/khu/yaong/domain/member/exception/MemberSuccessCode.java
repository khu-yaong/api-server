package com.khu.yaong.domain.member.exception;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements SuccessResponse {
    INFO_SUCCESS(HttpStatus.OK,"MEMBER_200","회원정보 조회에 성공했습니다."),
    WITHDRAW_SUCCESS(HttpStatus.OK,"MEMBER_201", "회원 탈퇴가 완료되었습니다.");

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

