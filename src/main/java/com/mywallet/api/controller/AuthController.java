package com.mywallet.api.controller;

import com.mywallet.api.dto.user.UserLoginDto;
import com.mywallet.api.dto.user.UserLoginResponseDto;
import com.mywallet.api.dto.user.UserRegisterDto;
import com.mywallet.api.dto.user.UserRegisterResponseDto;
import com.mywallet.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user",
        description = "Create a new user account with email, username and password",
        tags = "User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered with success!"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        authService.register(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserRegisterResponseDto(
            "User registered with success! Please verify your email."
        ));
    }

    @Operation(summary = "Login user",
        description = "Authenticate user with email and password",
        tags = "User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful!"),
        @ApiResponse(responseCode = "401", description = "Invalid email or password"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        String token = authService.login(userLoginDto);
        return ResponseEntity.status(HttpStatus.OK).body(new UserLoginResponseDto(token));
    }
}
