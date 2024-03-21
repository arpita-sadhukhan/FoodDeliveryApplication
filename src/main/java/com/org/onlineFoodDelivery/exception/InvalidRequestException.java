package com.org.onlineFoodDelivery.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BaseException{

    public InvalidRequestException(String message){
        super(message);
    }
    public InvalidRequestException(){}

    @Override
    public int getErrorCode() {
        errorCode = HttpStatus.NO_CONTENT.value();
        return errorCode;
    }
}
