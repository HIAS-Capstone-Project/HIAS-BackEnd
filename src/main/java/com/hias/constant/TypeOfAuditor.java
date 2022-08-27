package com.hias.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum TypeOfAuditor {

    MEMBER("M", "Member"),
    BUSINESS_APPRAISER("BA", "Business appraiser"),
    MEDICAL_APPRAISER("MA", "Medical appraiser"),
    HEAL_MODERATOR("HM", "Heal moderator"),
    ACCOUNTANT("ACC", "Accountant"),
    PAY("P", "Pay"),
    RETURN("RT", "Return");

    private String code;
    private String value;

    public static TypeOfAuditor findByString(String code) {
        for (TypeOfAuditor actionType : TypeOfAuditor.values()) {
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
