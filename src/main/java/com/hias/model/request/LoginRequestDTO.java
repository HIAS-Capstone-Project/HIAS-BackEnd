package com.hias.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "{AUTH_001}")
    private String username;

    @NotBlank(message = "{AUTH_002}")
    private String password;
}
