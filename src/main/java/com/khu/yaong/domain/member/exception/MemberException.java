package com.khu.yaong.domain.member.exception;

import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.type.ErrorResponse;

public class MemberException extends BaseException {
    public MemberException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
