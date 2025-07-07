package com.sau.jobservice.exception;

import org.springframework.http.HttpStatus;

/** Enum dışı status parametresi     – HTTP 400 */
public class InvalidStatusException extends BaseException {
    public InvalidStatusException(String status) {
        super("Geçersiz status: " + status,
                HttpStatus.BAD_REQUEST, "JOB-002");
    }
}
