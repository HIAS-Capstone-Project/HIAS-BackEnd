package com.hias.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusReasonCode {

    RE001("RE001", "License not enough."),
    RE002("RE002", "License is illegal.");

    private String code;
    private String value;

    public static StatusReasonCode findByString(String code) {
        for (StatusReasonCode statusReasonCode : StatusReasonCode.values()) {
            if (statusReasonCode.getCode().equalsIgnoreCase(code)) {
                return statusReasonCode;
            }
        }
        return null;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }
}
