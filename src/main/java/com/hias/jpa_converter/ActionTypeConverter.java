package com.hias.jpa_converter;

import com.hias.constant.ActionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActionTypeConverter implements AttributeConverter<ActionType, String> {
    @Override
    public String convertToDatabaseColumn(ActionType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ActionType convertToEntityAttribute(String dbData) {
        return ActionType.findByString(dbData);
    }
}
