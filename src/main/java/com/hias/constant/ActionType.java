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
    CANCEL("C", "Cancel"),
    RE_SUBMIT("RS", "Re submit"),
    REJECT("R", "Reject"),
    START_BUSINESS_VERIFY("SBV", "Start business verify"),
    START_MEDICAL_VERIFY("SMV", "Start medical verify"),
    START_APPROVAL_PROCESS("SAP", "Start approval process"),
    START_PAYING("SP", "Start paying"),
    BUSINESS_VERIFY("BV", "Business verify"),
    MEDICAL_VERIFY("MV", "Medical verify"),
    APPROVE("A", "Approve"),
    PAY("P", "Pay"),
    RETURN("RT", "Return"),
    ASSIGN_CLAIM_PROCESSOR("ACP", "Assign claim processor");

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
