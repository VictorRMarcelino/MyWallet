package com.mywallet.api.exception;

public class DestinyWalletNotFound extends BadRequestException {
    public DestinyWalletNotFound() {
        super("Destiny Wallet not found!");
    }
}
