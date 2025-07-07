package com.sau.applicationservice.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter @Builder
public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int            status;      // 400, 409, 500 ...
    private String         error;       // BAD_REQUEST, CONFLICT ...
    private String         message;     // İnsan-okunur açıklama
    private String         path;        // /api/auth/register
    private String         code;        // (opsiyonel) Uygulama-içi hata kodu
}

// Hata mesajı formatını tanımlama
// Bütün servisler aynı JSON şablonunu üretirse,
// hem Spring Cloud Gateway hem de frontend (Axios interceptors) tek bir yerden hata parse eder.