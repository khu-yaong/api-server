package com.khu.yaong.global.common.response.data;

import com.khu.yaong.global.common.type.SuccessResponse;
import org.springframework.http.HttpStatus;

public enum DataSuccessCode implements SuccessResponse {

    WORD_LIST_FOUND(HttpStatus.OK, "WORD2001", "야구 용어 목록을 조회하였습니다."),

    PLAYER_LIST_FOUND(HttpStatus.OK, "PLAYER2001", "야구 선수 목록을 조회하였습니다."),
    PLAYER_FOUND(HttpStatus.OK, "PLAYER2002", "야구 선수 정보를 조회하였습니다."),
    PITCHER_INFO_MOD_REQUESTED(HttpStatus.OK, "PLAYER2003", "투수 정보 수정을 요청하였습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

   DataSuccessCode(HttpStatus httpStatus, String code, String message) {
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
