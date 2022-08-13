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
    BENEFIT_ITEM_CODE_EXISTENCE("BENEFIT_ITEM_001"),
    STAFF_ID_EXISTENCE("MEMBER_001"),
    HEALCARD_NO_NOT_EXISTENCE("MEMBER_002"),
    SERVICE_PROVIDER_ID_EXISTENCE("SERVICE_PROVIDER_001"),
    CORPORATE_ID_EXISTENCE("CLIENT_001"),
    POLICY_CODE_EXISTENCE("POLICY_001"),
    EMPLOYEE_ID_EXISTENCE("EMPLOYEE_001"),
    ;

    private String code;
}
