package com.org.onlineFoodDelivery.exception;

public abstract class BaseException extends RuntimeException{

    public int errorCode;

    public BaseException(String message){
        super(message);
    }
    public BaseException(){}
    public abstract int getErrorCode();
}
