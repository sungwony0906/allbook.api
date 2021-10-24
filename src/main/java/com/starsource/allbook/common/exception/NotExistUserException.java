package com.starsource.allbook.common.exception;

public class NotExistUserException extends RuntimeException {

    public NotExistUserException(String message){
        super(message);
    }
}
