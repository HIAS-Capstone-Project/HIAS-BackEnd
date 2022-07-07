package com.hias.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BUSINESS_EMPLOYEE", schema = "HIAS")
@Getter
@Setter
public class BusinessEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUSINESS_EMPLOYEE_NO")
    private Long businessEmployeeNo;

    @Column(name = "EMPLOYEE_NO", nullable = false, unique = true)
    private Long employeeNo;

    @OneToMany(mappedBy = "businessEmployee")
    private List<EmployeeClient> employeeClients = new ArrayList<>();
}
