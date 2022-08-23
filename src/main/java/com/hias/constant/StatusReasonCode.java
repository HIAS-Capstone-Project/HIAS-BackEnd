package com.hias.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusReasonCode {

    RE001("RE001", "License is not enough."),
    RE002("RE002", "License is invalid."),
    RE003("RE003", "Other.");

    private String code;
    private String value;

    public static StatusReasonCode findByString(String code) {
        for (StatusReasonCode statusReasonCode : StatusReasonCode.values()) {
            if (statusReasonCode.getCode().equalsIgnoreCase(StringUtils.trim(code))) {
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
