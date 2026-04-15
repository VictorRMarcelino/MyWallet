package com.mywallet.api.controller;

import com.mywallet.api.dto.ResponseDto;
import com.mywallet.api.dto.user.UserLoginDto;
import com.mywallet.api.dto.user.UserLoginResponseDto;
import com.mywallet.api.dto.user.UserRegisterDto;
import com.mywallet.api.dto.user.UserRegisterResponseDto;
import com.mywallet.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user",
               description = "Create a new user account with email, username and password",
               tags = "User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered with success!"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody UserRegisterDto userRegisterDto) {
        userService.register(userRegisterDto);
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
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);
        return ResponseEntity.status(HttpStatus.OK).body(new UserLoginResponseDto(
            token,
            "Login successful!"
        ));
    }

    @Operation(summary = "Verify user email",
               description = "Verify the email of a user using the verification link",
               tags = "User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email verified with success!"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/verify-email/{userId}")
    public ResponseEntity<ResponseDto> verifyEmail(@PathVariable UUID userId) {
        userService.verifyEmail(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
            HttpStatus.OK.value(),
            "Email verified with success!"
        ));
    }
}

