package com.khu.yaong.global.common.response.data;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum WordSuccessCode implements SuccessResponse {

    WORD_LIST_FOUND(HttpStatus.OK, "WORD2001", "야구 용어 목록을 조회하였습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

   WordSuccessCode(HttpStatus httpStatus, String code, String message) {
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
