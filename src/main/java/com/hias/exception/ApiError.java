package com.hias.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError implements Serializable {

    private String errorMessage;

    private String fieldName;

    private HttpStatus httpStatus;
}
