package com.hias.jpa_converter;

import com.hias.constant.StatusCode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusCodeConverter implements AttributeConverter<StatusCode, String> {
    @Override
    public String convertToDatabaseColumn(StatusCode attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public StatusCode convertToEntityAttribute(String dbData) {
        return StatusCode.findByString(dbData);
    }
}
