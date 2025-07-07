package com.sau.applicationservice.exception;

import org.springframework.http.HttpStatus;

/** Job-Service 404 dönünce kullanılacak */
public class JobNotFoundException extends BaseException {
    public JobNotFoundException() {
        super("İş ilanı bulunamadı",
                HttpStatus.NOT_FOUND, "APP-005");
    }
}
