package com.sau.authservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBase(
            BaseException ex, HttpServletRequest req) {

        log.warn("Handled application error: {}", ex.getMessage());

        return build(ex.getStatus(), ex.getMessage(), ex.getCode(), req.getRequestURI());
    }

    // Bean Validation hataları (DTO üzerinde @NotBlank, @Email vb.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest req) {

        String details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Validation error");

        return build(HttpStatus.BAD_REQUEST, details, "COMMON-VALIDATION", req.getRequestURI());
    }

    // Yakalanmamış her şey
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(
            Exception ex, HttpServletRequest req) {

        log.error("Unexpected error", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error", "COMMON-500",
                req.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status,
                                                String message,
                                                String code,
                                                String path) {
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .timestamp(OffsetDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(message)
                        .code(code)
                        .path(path)
                        .build());
    }
}

