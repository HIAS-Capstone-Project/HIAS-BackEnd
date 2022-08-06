package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLAIM_REQUEST_HISTORY", schema = "HIAS")
@Getter
@Setter
public class ClaimRequestHistory extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLAIM_REQUEST_HISTORY_NO")
    private Long claimRequestHistoryNo;

    @Column(name = "EMPLOYEE_NO", insertable = false, updatable = false)
    private Long employeeNo;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_NO", nullable = false)
    private Employee employee;

    @Column(name = "CLAIM_NO", insertable = false, updatable = false)
    private Long claimNo;

    @ManyToOne
    @JoinColumn(name = "CLAIM_NO", nullable = false)
    private Claim claim;

    @Column(name = "REMARK")
    private String remark;
}
