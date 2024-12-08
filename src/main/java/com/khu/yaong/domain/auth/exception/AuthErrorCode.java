package com.khu.yaong.domain.auth.exception;

import com.khu.yaong.global.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorResponse {
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH400", "이미 존재하는 이메일입니다."),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH401", "이미 존재하는 닉네임입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH402", "회원 정보를 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH403", "유효하지 않은 리프레시 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH404", "토큰이 만료되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "AUTH405", "유효하지 않은 토큰 형식입니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH500", "서버 오류가 발생했습니다."),

    // 이메일 인증 관련
    EMAIL_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH406", "이메일 인증 코드를 찾을 수 없습니다."),
    EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "AUTH407", "이메일 인증 코드가 일치하지 않습니다."),
    EMAIL_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH408", "이메일 인증 코드가 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String code, String message) {
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
