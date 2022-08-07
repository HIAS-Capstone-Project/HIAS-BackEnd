package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmploymentTypeResponseDTO implements Serializable {
    private Long employmentTypeNo;

    private String employmentTypeCode;

    private String employmentTypeName;
}
