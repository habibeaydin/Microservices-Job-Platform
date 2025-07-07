package com.sau.jobservice.exception;

import org.springframework.http.HttpStatus;

/** Yalnızca işveren rolü gerekli   – HTTP 403 */
public class EmployerRoleRequiredException extends BaseException {
    public EmployerRoleRequiredException() {
        super("Sadece işverenler bu işlemi yapabilir",
                HttpStatus.FORBIDDEN, "JOB-001");
    }
}
