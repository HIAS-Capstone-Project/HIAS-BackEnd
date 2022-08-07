package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DEPARTMENT", schema = "HIAS")
@Getter
@Setter
public class Department extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_NO")
    private Long departmentNo;

    @Column(name = "DEPARTMENT_CODE")
    private String departmentCode;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Employee> employeeList = new ArrayList<>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<DepartmentEmploymentType> departmentEmploymentTypes = new ArrayList<>();
}
