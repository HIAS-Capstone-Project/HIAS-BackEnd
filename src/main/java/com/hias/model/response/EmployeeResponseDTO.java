package com.hias.model.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeResponseDTO implements Serializable {
    private Long employeeNo;

    private String employeeID;

    private String employeeName;

    private String phoneNumber;

    private String email;

    private String address;

    private Long departmentNo;

    private LocalDate dob;
}
