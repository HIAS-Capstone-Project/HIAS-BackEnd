package com.hias.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "{AUTH_001}")
    private String username;

    @NotBlank(message = "{AUTH_002}")
    private String password;

    @NotBlank(message = "{AUTH_003}")
    private String roleName;
}
