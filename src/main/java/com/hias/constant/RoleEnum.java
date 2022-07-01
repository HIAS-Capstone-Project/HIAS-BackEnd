package com.hias.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum RoleEnum {
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    MEMBER("MEMBER"),
    COMPANY("COMPANY"),
    EMPLOYEE("EMPLOYEE");

    private String name;
}
