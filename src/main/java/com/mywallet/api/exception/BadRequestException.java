package com.mywallet.api.exception;

abstract public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
