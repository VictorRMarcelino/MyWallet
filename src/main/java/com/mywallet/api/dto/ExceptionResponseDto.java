package com.mywallet.api.dto;

public record ExceptionResponseDto (
    int status,
    String message
){}
