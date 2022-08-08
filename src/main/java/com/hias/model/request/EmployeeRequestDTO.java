package com.hias.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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

    private String address;

    private Long departmentNo;

    private Long employmentTypeNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;
}
