package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DEPARTMENT", schema = "HIAS")
@Getter
@Setter
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_NO")
    private Long departmentNo;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @Column(name = "REMARK")
    private String remark;
}
