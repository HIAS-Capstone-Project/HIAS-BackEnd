package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DepartmentResponseDTO implements Serializable {

    private Long departmentNo;

    private String departmentCode;

    private String departmentName;
}
