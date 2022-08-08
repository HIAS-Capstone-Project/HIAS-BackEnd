package com.hias.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String username;

    private String password;

    private String roleName;

    private Long clientNo;

    private Long memberNo;

    private Long employeeNo;

    private Long serviceProviderNo;
}
