package com.beautyhub.beautyhubbackend.exception;

import com.beautyhub.beautyhubbackend.domain.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

// Only handles REST API controllers
@RestControllerAdvice(basePackages =
        "com.beautyhub.beautyhubbackend.controller.api")
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(
                    GlobalExceptionHandler.class);

    // 404 — Entity not found
    @ExceptionHandler(
            EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleEntityNotFound(
            EntityNotFoundException ex) {
        log.error("Entity not found: {}",
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()));
    }

    // 404 — Our custom service exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse>
    handleRuntimeException(
            RuntimeException ex) {
        log.error("RuntimeException: {}",
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()));
    }

    // 409 — FK constraint on delete
    @ExceptionHandler(
            DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse>
    handleDataIntegrity(
            DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}",
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "Cannot delete — this record " +
                                "is linked to other data. " +
                                "Remove related records first."));
    }

    // 400 — @Valid failed in REST API
    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() +
                        ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Validation failed: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        errors));
    }

    // 400 — Illegal argument
    @ExceptionHandler(
            IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse>
    handleIllegalArgument(
            IllegalArgumentException ex) {
        log.error("Illegal argument: {}",
                ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()));
    }

    // 404 — Static resources
    @ExceptionHandler(
            NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResource(
            NoResourceFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    // 500 — Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(Exception ex) {
        log.error("Unexpected exception: {}",
                ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR
                                .value(),
                        "An unexpected error occurred"));
    }
}