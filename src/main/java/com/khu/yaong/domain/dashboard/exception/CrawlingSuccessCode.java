package com.khu.yaong.domain.dashboard.exception;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum CrawlingSuccessCode implements SuccessResponse {
    PARSE_SUCCESS(HttpStatus.OK, "CRAWLING201", "HTML 페이지를 성공적으로 파싱했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CrawlingSuccessCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}