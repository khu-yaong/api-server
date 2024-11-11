package com.khu.yaong.global.common.response;

import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalResponseAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException e) {
        ErrorResponse errorResponse = e.getErrorType();
        return new ResponseEntity<>(
                ApiResponse.error(errorResponse),
                e.getHttpStatus()
        );

    }
}

