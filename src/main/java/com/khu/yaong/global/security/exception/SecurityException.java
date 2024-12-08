package com.khu.yaong.global.security.exception;

import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.type.ErrorResponse;

public class SecurityException extends BaseException {
    public SecurityException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}