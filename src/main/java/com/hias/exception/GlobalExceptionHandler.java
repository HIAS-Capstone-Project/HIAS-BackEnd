package com.hias.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HIASException.class)
    public ResponseEntity<ApiError> handleHIASException(HIASException ex) {
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatus());
    }
}
