package com.org.onlineFoodDelivery.exception;

import org.springframework.http.HttpStatus;

public class ObjectCreationException extends BaseException{

    public ObjectCreationException(String message){
        super(message);
    }
    public ObjectCreationException(){}

    @Override
    public int getErrorCode() {
        errorCode = HttpStatus.BAD_REQUEST.value();
        return errorCode;
    }
}
