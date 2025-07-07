package com.sau.applicationservice.exception;

import org.springframework.http.HttpStatus;

/** İşveren olmayan kullanıcı başvuruları görüntülüyor */
public class EmployerRoleRequiredException extends BaseException {
    public EmployerRoleRequiredException() {
        super("Sadece işverenler başvuruları görebilir",
                HttpStatus.FORBIDDEN, "APP-004");
    }
}
