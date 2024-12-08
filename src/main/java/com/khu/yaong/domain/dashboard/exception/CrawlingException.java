package com.khu.yaong.domain.dashboard.exception;

import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.type.ErrorResponse;

public class CrawlingException extends BaseException {
    public CrawlingException(ErrorResponse errorResponse){
        super(errorResponse);
    }
}
