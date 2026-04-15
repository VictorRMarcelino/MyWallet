package com.mywallet.api.dto.user;

import jakarta.validation.constraints.Email;

public record UserLoginDto (
    @Email(message = "Invalid email format")
    String email,
    String password
){}
