package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory
            .getLogger(GlobalExceptionHandler.class);

    // Handles "not found" errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse>
    handleRuntimeException(
            RuntimeException ex) {
        log.error("RuntimeException: {}",
                ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    // Ignores favicon and static resource errors
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResource(
            NoResourceFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    // Handles ALL other unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(Exception ex) {
        log.error("Unexpected exception: {}",
                ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}