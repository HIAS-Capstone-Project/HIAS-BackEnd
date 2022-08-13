package com.hias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HIASException.class)
    public ResponseEntity<ApiError> handleHIASException(HIASException ex) {
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatus());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleException(Exception ex) {
//        return new ResponseEntity<>(ApiError.builder()
//                .errorMessage(ex.getMessage())
//                .httpStatus(HttpStatus.BAD_REQUEST)
//                .build(), HttpStatus.BAD_REQUEST);
//    }
}
