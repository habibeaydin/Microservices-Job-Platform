package com.sau.applicationservice.exception;

import org.springframework.http.HttpStatus;

/** Aday aynı ilana ikinci kez başvuruyor */
public class AlreadyAppliedException extends BaseException {
    public AlreadyAppliedException() {
        super("Bu ilana zaten başvurdunuz",
                HttpStatus.CONFLICT, "APP-003");
    }
}
