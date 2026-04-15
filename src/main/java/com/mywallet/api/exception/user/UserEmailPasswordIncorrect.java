package com.mywallet.api.exception.user;

import com.mywallet.api.exception.UnauthorizedException;

public class UserEmailPasswordIncorrect extends UnauthorizedException {
    public UserEmailPasswordIncorrect() {
        super("Email or Password are Incorrect!");
    }
}
