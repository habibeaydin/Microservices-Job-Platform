package com.sau.jobservice.exception;

import org.springframework.http.HttpStatus;

/** İlan üzerinde yetkisiz işlem     – HTTP 403 */
public class UnauthorizedJobAccessException extends BaseException {
    public UnauthorizedJobAccessException() {
        super("Bu ilana erişim izniniz yok",
                HttpStatus.FORBIDDEN, "JOB-004");
    }
}
