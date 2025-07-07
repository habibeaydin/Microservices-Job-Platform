package com.sau.authservice.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BaseException {
    public EmailAlreadyExistsException() {
        super("Email already in use", HttpStatus.CONFLICT, "AUTH-001");
    }
}
