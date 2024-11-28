package com.khu.yaong.global.common.response.s3;

import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum S3ErrorCode implements ErrorResponse {

    FILE_IS_NULL(HttpStatus.BAD_REQUEST, "S34001", "파일이 입력되지 않았습니다."),
    IO_EXCEPTION(HttpStatus.BAD_REQUEST, "S34002", "IO 오류가 발생했습니다."),
    BAD_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "S34003", "잘못된 확장자입니다."),
    PUT_OBJECT_EXCEPTION(HttpStatus.BAD_REQUEST, "S34004", "이미지 업로드하는 과정에서 오류가 발생했습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    S3ErrorCode(HttpStatus httpStatus, String code, String message) {
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
