package com.example.pessoas.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustoError.class)
    public ResponseEntity<String> runtimeException(CustoError ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Aconteceu um : " + ex.getMessage());
    }
}
