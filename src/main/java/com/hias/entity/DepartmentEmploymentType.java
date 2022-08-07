package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DEPARTMENT_EMPLOYMENT_TYPE", schema = "HIAS")
@Getter
@Setter
public class DepartmentEmploymentType extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_EMPLOYMENT_TYPE_NO")
    private Long departmentEmploymentTypeNo;

    @Column(name = "EMPLOYMENT_TYPE_NO", insertable = false, updatable = false)
    private Long employmentTypeNo;

    @ManyToOne
    @JoinColumn(name = "EMPLOYMENT_TYPE_NO", nullable = false)
    private EmploymentType employmentType;

    @Column(name = "DEPARTMENT_NO", insertable = false, updatable = false)
    private Long departmentNo;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_NO", nullable = false)
    private Department department;
}
