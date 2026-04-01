package com.micnusz.orderService.Exception;

public class UserServiceUnavailableException extends RuntimeException {
    public UserServiceUnavailableException(String msg) {
        super(msg);
    }
}
