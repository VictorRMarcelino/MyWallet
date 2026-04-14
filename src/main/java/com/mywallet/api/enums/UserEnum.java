package com.mywallet.api.enums;

public enum UserEnum {

    EMAIL_NOT_VERIFIED(0),
    EMAIL_VERIFIED(1);

    public int value;

    UserEnum(int value) {
        this.value = value;
    }
}

