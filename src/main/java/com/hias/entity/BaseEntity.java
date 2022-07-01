package com.hias.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity extends SoftDeleteEntity {

    @Column(name = "STATUS_CODE", length = 3)
    private String statusCode;
}
