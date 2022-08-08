package com.hias.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DepartmentResponseDTO implements Serializable {

    private Long departmentNo;

    private String departmentCode;

    private String departmentName;

    private List<EmploymentTypeResponseDTO> list = new ArrayList<>();
}
