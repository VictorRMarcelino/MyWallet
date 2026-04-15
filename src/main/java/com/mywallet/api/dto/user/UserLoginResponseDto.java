package com.mywallet.api.dto.user;

public record UserLoginResponseDto (
    String token,
    String message
){}

