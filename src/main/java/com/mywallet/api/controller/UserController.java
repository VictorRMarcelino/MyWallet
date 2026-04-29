package com.mywallet.api.controller;

import com.mywallet.api.dto.ResponseDto;
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

