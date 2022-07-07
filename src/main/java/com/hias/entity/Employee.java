package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE_NO", schema = "HIAS")
@Getter
@Setter
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_NO")
    private Long employeeNo;

    @Column(name = "EMPLOYEE_ID")
    private String employeeID;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DEPARTMENT_NO", insertable = false, updatable = false)
    private Long departmentNo;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_NO", nullable = false)
    private Department department;
}