package com.sau.applicationservice.exception;

import org.springframework.http.HttpStatus;

/** Aday olmayan kullanıcı başvuru yapmaya çalıştı */
public class CandidateRoleRequiredException extends BaseException {
    public CandidateRoleRequiredException() {
        super("Sadece adaylar ilana başvurabilir",
                HttpStatus.FORBIDDEN, "APP-001");
    }
}
