package com.khu.yaong.global.common.response.post;

import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorResponse {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4001", "게시글을 찾을 수 없습니다."),
    DUPLICATE_POST_LIKES_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "POST4002", "게시글에 중복으로 좋아요를 누를 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    PostErrorCode(HttpStatus httpStatus, String code, String message) {
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
