package com.khu.yaong.global.common.response.post;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum PostSuccessCode implements SuccessResponse {

    POST_CREATED(HttpStatus.CREATED, "POST2001", "게시글이 생성되었습니다."),
    POST_UPDATED(HttpStatus.OK, "POST2002", "게시글이 수정되었습니다."),
    POST_FOUND(HttpStatus.OK, "POST2003", "게시글 조회에 성공하였습니다."),
    POST_LIKED(HttpStatus.CREATED, "POST2004", "게시글 좋아요에 성공하였습니다."),
    POST_LIKE_CANCELED(HttpStatus.OK, "POST2005", "게시글 좋아요를 취소하였습니다."),

    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    PostSuccessCode(HttpStatus httpStatus, String code, String message) {
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
