package com.mywallet.api.enums;

public enum WalletTransactionEnum {

    TYPE_DEPOSIT(1),
    TYPE_PAYMENT(2);

    public int value;

    WalletTransactionEnum(int value) {
        this.value = value;
    }
}
