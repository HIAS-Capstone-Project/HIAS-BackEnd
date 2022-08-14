package com.hias.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusCode {
    ACTIVE("ACT", "Active"),
    DRAFT("DRA", "Draft"),
    TERMINATED("TER", "Terminated"),
    SUBMITTED("SUB", "Submitted"),
    APPROVED("APR", "Approved"),
    PAYMENT_PROCESSING("PAY", "Payment Processing"),
    SETTLED("SET", "Settled");

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

    @JsonValue
    public String getCode() {
        return this.code;
    }
}
