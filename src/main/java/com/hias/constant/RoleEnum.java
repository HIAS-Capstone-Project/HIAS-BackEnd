package com.hias.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN"),
    ROLE_MEMBER("ROLE_MEMBER"),
    ROLE_CLIENT("ROLE_CLIENT"),
    ROLE_SERVICE_PROVIDER("ROLE_SERVICE_PROVIDER"),
    ROLE_BUSINESS_EMPLOYEE("ROLE_BUSINESS_EMPLOYEE"),
    ROLE_MEDICAL_APPRAISER("ROLE_MEDICAL_APPRAISER"),
    ROLE_BUSINESS_APPRAISER("ROLE_BUSINESS_APPRAISER"),
    ROLE_HEALTH_MODERATOR("ROLE_HEALTH_MODERATOR"),
    ROLE_ACCOUNTANT("ROLE_ACCOUNTANT");

    private String name;
}
