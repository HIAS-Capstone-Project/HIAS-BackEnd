package com.hias.jpa_converter;

import com.hias.constant.GenderEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderEnumConverter implements AttributeConverter<GenderEnum, String> {

    @Override
    public String convertToDatabaseColumn(GenderEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String dbData) {
        return GenderEnum.findByString(dbData);
    }
}
