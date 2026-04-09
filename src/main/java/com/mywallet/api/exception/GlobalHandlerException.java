package com.mywallet.api.exception;

import com.mywallet.api.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ResponseEntity<ExceptionResponseDto> handleBadRequestException(BadRequestException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleGeneralException(Exception exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }
}
