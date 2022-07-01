package com.hias.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "username must not be null")
    private String username;

    @NotBlank(message = "password must not be null")
    private String password;

    @NotBlank(message = "roleName must not be null")
    private String roleName;
}
