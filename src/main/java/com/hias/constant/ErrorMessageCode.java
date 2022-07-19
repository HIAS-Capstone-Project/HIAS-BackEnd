package com.hias.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorMessageCode {

    USERNAME_OR_PASSWORD_INCORRECT("AUTH_001"),
    BENEFIT_CODE_EXISTENCE("BEN_001"),
    STAFF_ID_EXISTENCE("MEMBER_001"),
    SERVICE_PROVIDER_ID_EXISTENCE("SERVICE_PROVIDER_001"),
    CORPORATE_ID_EXISTENCE("CLIENT_001"),
    POLICY_CODE_EXISTENCE("POLICY_001");

    private String code;
}
