package com.mywallet.api.exception.user;

import com.mywallet.api.exception.ResourceNotFoundException;

public class UserEmailPasswordIncorrect extends ResourceNotFoundException {
    public UserEmailPasswordIncorrect() {
        super("Email or Password are Incorrect!");
    }
}
