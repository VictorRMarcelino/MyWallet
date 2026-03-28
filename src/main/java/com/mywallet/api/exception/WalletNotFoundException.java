package com.mywallet.api.exception;

public class WalletNotFoundException extends ResourceNotFoundException {
    public WalletNotFoundException() {
        super("Wallet not found!");
    }
}
