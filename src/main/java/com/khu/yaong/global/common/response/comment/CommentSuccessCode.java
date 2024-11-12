package com.khu.yaong.global.common.response.comment;

import com.khu.yaong.global.common.response.SuccessCode;
import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum CommentSuccessCode implements SuccessResponse {

    COMMENT_CREATED(HttpStatus.CREATED, "COMMENT2001", "댓글이 생성되었습니다."),
    COMMENT_DELETED(HttpStatus.OK, "COMMENT2002", "댓글이 삭제되었습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommentSuccessCode(HttpStatus httpStatus, String code, String message) {
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
