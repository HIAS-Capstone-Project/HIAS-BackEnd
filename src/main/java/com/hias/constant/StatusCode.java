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
    CANCELED("CXL", "Canceled"),
    SUBMITTED("SUB", "Submitted"),
    BUSINESS_VERIFIED("BV", "Business Verified"),
    BUSINESS_VERIFYING("BVY", "Business Verifying"),
    MEDICAL_VERIFIED("MV", "Medical Verified"),
    MEDICAL_VERIFYING("MVY", "Medical Verifying"),
    WAITING_FOR_APPROVAL("WFA", "Waiting for approval"),
    APPROVED("APR", "Approved"),
    PAYMENT_PROCESSING("PAY", "Payment Processing"),
    SETTLED("SET", "Settled"),
    REJECTED("REJ", "Rejected");

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
