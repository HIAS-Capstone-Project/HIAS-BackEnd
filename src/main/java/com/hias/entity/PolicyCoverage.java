package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POLICY_COVERAGE", schema = "HIAS")
@Getter
@Setter
public class PolicyCoverage extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLICY_COVERAGE_NO")
    private Long policyCoverageNo;

    @Column(name = "POLICY_NO", insertable = false, updatable = false)
    private Long policyNo;

    @ManyToOne
    @JoinColumn(name = "POLICY_NO", nullable = false)
    private Policy policy;

    @Column(name = "BENEFIT_NO", insertable = false, updatable = false)
    private Long benefitNo;

    @ManyToOne
    @JoinColumn(name = "BENEFIT_NO", nullable = false)
    private Benefit benefit;
}
