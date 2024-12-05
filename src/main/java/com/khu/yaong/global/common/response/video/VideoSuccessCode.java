package com.khu.yaong.global.common.response.video;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum VideoSuccessCode implements SuccessResponse {

    VIDEO_VIEW_LIST_UPDATED(HttpStatus.CREATED,"VIDEO2001", "비디오 조회 내역이 업데이트 되었습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    VideoSuccessCode(HttpStatus httpStatus, String code, String message) {
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
