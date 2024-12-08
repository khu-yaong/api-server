package com.khu.yaong.domain.dashboard.exception;

import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum CrawlingErrorCode implements ErrorResponse {
    INVALID_URL(HttpStatus.BAD_REQUEST, "CRAWL400", "유효하지 않은 URL입니다."),
    FETCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CRAWL500", "페이지 가져오기에 실패했습니다."),
    PARSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CRAWL501", "페이지 파싱에 실패했습니다."),
    TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "CRAWL408", "요청 시간이 초과되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    CrawlingErrorCode(HttpStatus httpStatus, String code, String message) {
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
