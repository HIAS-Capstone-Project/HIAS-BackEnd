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

    SAVE_DRAFT("SD", "Save Draft"),
    SUBMIT("S", "Submit"),
    RE_SUBMIT("RS", "Re submit"),
    REJECT("R", "Reject"),
    START_PROGRESS("SP", "Start progress"),
    PAY("P", "Pay"),
    RETURN("RT", "Return");

    private String code;
    private String value;

    public static ActionType findByString(String code) {
        for (ActionType actionType : ActionType.values()) {
            if (actionType.getCode().equalsIgnoreCase(StringUtils.trim(code))) {
                return actionType;
            }
        }
        return null;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }
}
