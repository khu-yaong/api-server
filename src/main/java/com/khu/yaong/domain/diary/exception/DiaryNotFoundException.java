package com.khu.yaong.domain.diary.exception;

public class DiaryNotFoundException extends RuntimeException{

    public DiaryNotFoundException(String message){
        super(message);
    }

    public DiaryNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
