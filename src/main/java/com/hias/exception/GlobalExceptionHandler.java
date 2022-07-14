package com.hias.exception;

import com.hias.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * process exceptions thrown by other modules
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicationValueException.class)
    public ResponseEntity HandleDuplicationValueException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(errorResponse.getError(),errorResponse.getStatus());
    }


}