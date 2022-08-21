package com.hias.constant;

import com.fasterxml.jackson.annotation.JsonValue;
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
        for (RecordSource recordSource : RecordSource.values()) {
            if (recordSource.getCode().equalsIgnoreCase(code)) {
                return recordSource;
            }
        }
        return null;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }
}
