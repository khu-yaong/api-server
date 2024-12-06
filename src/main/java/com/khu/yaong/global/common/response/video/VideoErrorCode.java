package com.khu.yaong.global.common.response.video;

import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum VideoErrorCode implements ErrorResponse {

    LAMBDA_CALL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "VIDEO5001", "Lambda 호출 과정에서 문제가 발생했습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    VideoErrorCode(HttpStatus httpStatus, String code, String message) {
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
