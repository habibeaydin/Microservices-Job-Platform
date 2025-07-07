package com.sau.userprofileservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("Kullanıcı bulunamadı",
                HttpStatus.NOT_FOUND,
                "USER-002");
    }
}
