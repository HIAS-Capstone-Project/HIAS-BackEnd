package com.hias.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusCode {
    ACTIVE("ACT", "Active"),
    TERMINATED("TER", "Terminated");

    private String code;
    private String value;

    public static StatusCode findByString(String code) {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.getCode().equalsIgnoreCase(code)) {
                return statusCode;
            }
        }
        return null;
    }
}
