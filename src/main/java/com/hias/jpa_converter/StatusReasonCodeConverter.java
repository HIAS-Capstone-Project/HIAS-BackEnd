package com.hias.jpa_converter;

import com.hias.constant.StatusCode;
import com.hias.constant.StatusReasonCode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusReasonCodeConverter implements AttributeConverter<StatusReasonCode, String> {
    @Override
    public String convertToDatabaseColumn(StatusReasonCode attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public StatusReasonCode convertToEntityAttribute(String dbData) {
        return StatusReasonCode.findByString(dbData);
    }
}
