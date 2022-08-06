package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYMENT_TYPE", schema = "HIAS")
@Getter
@Setter
public class EmploymentType extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYMENT_TYPE_NO")
    private Long employmentTypeNo;

    @Column(name = "EMPLOYMENT_TYPE_CODE")
    private String employmentTypeCode;

    @Column(name = "EMPLOYMENT_TYPE_NAME")
    private String employmentTypeName;

    @OneToMany(mappedBy = "employmentType")
    private List<Employee> employeeList = new ArrayList<>();
}
