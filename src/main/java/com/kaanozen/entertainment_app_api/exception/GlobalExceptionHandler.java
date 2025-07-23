package com.kaanozen.entertainment_app_api.exception;

import com.kaanozen.entertainment_app_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice // Bu sınıfın tüm Controller'lar için merkezi bir hata yakalayıcı olduğunu belirtir.
public class GlobalExceptionHandler {

    // Bu metot, sadece IllegalStateException tipindeki hataları yakalar.
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        // Hata mesajını ve diğer bilgileri içeren standart bir DTO oluşturuyoruz.
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(), // 409 Conflict: Kaynak çakışması (aynı email)
                ex.getMessage(), // Bizim fırlattığımız "Email already in use" mesajı
                LocalDateTime.now()
        );
        // Bu DTO'yu 409 status kodu ile birlikte frontend'e gönderiyoruz.
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}