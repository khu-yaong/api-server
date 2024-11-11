package com.khu.yaong.global.common.type;

import org.springframework.http.HttpStatus;


public interface SuccessResponse {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
