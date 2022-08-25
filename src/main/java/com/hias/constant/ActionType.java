package com.hias.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ActionType {

    SAVE_DRAFT,
    SUBMIT,
    RE_SUBMIT,
    REJECT,
    START_PROGRESS,
    PAY,
    RETURN;

    private String code;
    private String value;

    public static ActionType findByString(String code) {
        for (ActionType statusReasonCode : ActionType.values()) {
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
