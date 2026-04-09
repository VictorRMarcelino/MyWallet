package com.mywallet.api.dto.user;

public record UserRegisterDto (
    String email,
    String password,
    String username
){}
