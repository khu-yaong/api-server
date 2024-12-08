package com.khu.yaong.domain.auth.exception;

import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.type.ErrorResponse;

public class AuthException extends BaseException {
    public AuthException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
