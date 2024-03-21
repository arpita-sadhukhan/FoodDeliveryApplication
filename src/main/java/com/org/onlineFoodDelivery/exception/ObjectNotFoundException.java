package com.org.onlineFoodDelivery.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends BaseException{

    public ObjectNotFoundException(String message){
        super(message);
    }
    public ObjectNotFoundException(){}
    @Override
    public int getErrorCode() {
        errorCode = HttpStatus.NO_CONTENT.value();
        return errorCode;
    }
}
