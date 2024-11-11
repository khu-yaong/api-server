package com.khu.yaong.global.common.exception;

import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public BaseException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;

    }
    public ErrorResponse getErrorType() {
        return this.errorResponse;
    }

    public int getHttpCode() {
        return this.errorResponse.getHttpStatus().value();
    }

    public HttpStatus getHttpStatus() {
        return this.errorResponse.getHttpStatus();
    }

}
