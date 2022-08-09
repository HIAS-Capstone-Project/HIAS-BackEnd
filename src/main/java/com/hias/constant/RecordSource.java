package com.hias.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RecordSource {
    M("M", "Member"),
    SVP("SVP", "Service Provider");

    private String code;
    private String value;

    public static RecordSource findByString(String code) {
        for (RecordSource statusCode : RecordSource.values()) {
            if (statusCode.getCode().equalsIgnoreCase(code)) {
                return statusCode;
            }
        }
        return null;
    }
}
