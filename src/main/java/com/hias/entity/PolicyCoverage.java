package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POLICY_COVERAGE", schema = "HIAS")
public class PolicyCoverage extends BaseEntity {

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

    @Column(name = "BUDGET_AMOUNT")
    private BigDecimal budgetAmount;
}
