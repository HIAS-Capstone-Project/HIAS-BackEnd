package com.hias.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum GenderEnum {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    OTHER("O", "Other");

    private String code;
    private String value;

    public static GenderEnum findByString(String code) {
        for (GenderEnum statusCode : GenderEnum.values()) {
            if (statusCode.getCode().equalsIgnoreCase(code)) {
                return statusCode;
            }
        }
        return null;
    }

    @JsonValue
    public String getCode() {
        return this.getCode();
    }
}
