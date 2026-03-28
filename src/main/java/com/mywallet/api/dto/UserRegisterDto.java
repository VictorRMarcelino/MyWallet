package com.mywallet.api.dto;

public record UserRegisterDto (
    String email,
    String password,
    String username
){}
