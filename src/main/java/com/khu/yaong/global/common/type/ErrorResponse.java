package com.khu.yaong.global.common.type;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}


