package com.hias.model.request;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeRequestDTO implements Serializable {
    private Long employeeNo;

    private String employeeID;

    private String employeeName;

    private String phoneNumber;

    private String email;

    private Long departmentNo;
}
