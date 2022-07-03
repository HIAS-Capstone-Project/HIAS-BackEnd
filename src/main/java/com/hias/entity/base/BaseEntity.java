package com.hias.entity.base;

import com.hias.constant.StatusCode;
import com.hias.jpa_converter.StatusCodeConverter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity extends SoftDeleteEntity {

    @Column(name = "STATUS_CODE", length = 3, nullable = false)
    @Convert(converter = StatusCodeConverter.class)
    private StatusCode statusCode = StatusCode.ACTIVE;
}
