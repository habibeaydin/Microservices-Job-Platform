package com.sau.jobservice.exception;

import org.springframework.http.HttpStatus;

/** İlan bulunamadı                  – HTTP 404 */
public class JobNotFoundException extends BaseException {
    public JobNotFoundException(Long id) {
        super("İş ilanı bulunamadı (id=" + id + ")",
                HttpStatus.NOT_FOUND, "JOB-003");
    }
}
