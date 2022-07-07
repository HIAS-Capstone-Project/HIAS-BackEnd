package com.hias.entity.base;

import com.hias.constant.StatusCode;
import com.hias.jpa_converter.StatusCodeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Getter
@Setter
public class BaseEntity extends SoftDeleteEntity {

    @Column(name = "STATUS_CODE", length = 3, nullable = false)
    @Convert(converter = StatusCodeConverter.class)
    private StatusCode statusCode = StatusCode.ACTIVE;
}
