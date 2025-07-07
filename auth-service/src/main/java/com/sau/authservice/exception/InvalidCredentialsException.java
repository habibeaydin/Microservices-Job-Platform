package com.sau.authservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super("Invalid credentials", HttpStatus.UNAUTHORIZED, "AUTH-002");
    }
}
