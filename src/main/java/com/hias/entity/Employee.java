package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE", schema = "HIAS")
@Getter
@Setter
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_NO")
    private Long employeeNo;

    @Column(name = "EMPLOYEE_ID", updatable = false)
    private String employeeID;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "DEPARTMENT_NO", insertable = false, updatable = false)
    private Long departmentNo;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_NO", nullable = false)
    private Department department;

    @Column(name = "EMPLOYMENT_TYPE_NO", insertable = false, updatable = false)
    private Long employmentTypeNo;

    @ManyToOne
    @JoinColumn(name = "EMPLOYMENT_TYPE_NO", nullable = false)
    private EmploymentType employmentType;
}
