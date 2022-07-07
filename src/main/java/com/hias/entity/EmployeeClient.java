package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE_CLIENT", schema = "HIAS")
@Getter
@Setter
public class EmployeeClient extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_CLIENT_NO")
    private Long employeeClientNo;

    @Column(name = "BUSINESS_EMPLOYEE_NO", insertable = false, updatable = false)
    private Long businessEmployeeNo;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_EMPLOYEE_NO", nullable = false)
    private BusinessEmployee businessEmployee;

    @Column(name = "CLIENT_NO", insertable = false, updatable = false)
    private Long clientNo;

    @ManyToOne
    @JoinColumn(name = "CLIENT_NO", nullable = false)
    private Client client;
}
