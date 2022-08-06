package com.hias.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RecordSource {
    MS("MS", "Member Submission"),
    SS("SS", "Service Provider Submission");

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
