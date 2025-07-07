package com.sau.userprofileservice.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException() {
        super("Bu kullanıcı profili zaten oluşturulmuş",
                HttpStatus.CONFLICT,
                "USER-001");
    }
}
