package com.hias.jpa_converter;

import com.hias.constant.RecordSource;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RecordSourceConverter implements AttributeConverter<RecordSource, String> {

    @Override
    public String convertToDatabaseColumn(RecordSource attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public RecordSource convertToEntityAttribute(String dbData) {
        return RecordSource.findByString(dbData);
    }
}
