package com.mywallet.api.exception;

public class NotEnoughBalanceException extends BadRequestException {
    public NotEnoughBalanceException() {
        super("Not enough balance to realize the operation!");
    }
}
