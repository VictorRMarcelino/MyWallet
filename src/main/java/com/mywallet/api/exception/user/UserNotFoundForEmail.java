package com.mywallet.api.exception.user;

import com.mywallet.api.exception.ResourceNotFoundException;

public class UserNotFoundForEmail extends ResourceNotFoundException {
    public UserNotFoundForEmail(String email) {
        super("User not found for the email " + email);
    }
}
