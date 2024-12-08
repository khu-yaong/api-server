package com.khu.yaong.domain.member.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message){
        super(message);
    }
}
