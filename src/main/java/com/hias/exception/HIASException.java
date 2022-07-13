package com.hias.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HIASException extends Exception {
    private ApiError apiError;
    private HttpStatus httpStatus;

    public static final HIASException buildHIASException(String errorMessage, HttpStatus httpStatus) {
        return HIASException.builder()
                .httpStatus(httpStatus)
                .apiError(ApiError.builder()
                        .errorMessage(errorMessage)
                        .httpStatus(httpStatus)
                        .build()).build();
    }
}
