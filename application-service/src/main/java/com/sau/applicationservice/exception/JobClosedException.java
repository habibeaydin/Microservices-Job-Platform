package com.sau.applicationservice.exception;

import org.springframework.http.HttpStatus;

/** İş ilanı kapalı */
public class JobClosedException extends BaseException {
    public JobClosedException() {
        super("Bu ilana başvuru alınmıyor",
                HttpStatus.BAD_REQUEST, "APP-002");
    }
}
