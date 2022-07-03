package com.hias.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum RoleEnum {
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    MEMBER("MEMBER"),
    CLIENT("CLIENT"),
    EMPLOYEE("EMPLOYEE"),
    SERVICE_PROVIDER("SERVICE_PROVIDER"),
    CLAIM_PROCESSOR("CLAIM_PROCESSOR");

    private String name;
}
