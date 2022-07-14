package com.hias.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorMessageCode {

    USERNAME_OR_PASSWORD_INCORRECT("AUTH_001"),

    BENEFIT_CODE_EXISTENCE("BEN_001");

    private String code;
}
