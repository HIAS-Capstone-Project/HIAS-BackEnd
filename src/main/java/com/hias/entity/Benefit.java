package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BENEFIT", schema = "HIAS")
@Getter
@Setter
public class Benefit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BENEFIT_NO")
    private Long benefitNo;

    @Column(name = "BENEFIT_CODE", updatable = false)
    private String benefitCode;

    @Column(name = "BENEFIT_NAME")
    private String benefitName;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "benefit", fetch = FetchType.LAZY)
    private List<PolicyCoverage> policyCoverageList = new ArrayList<>();

    @OneToMany(mappedBy = "benefit", fetch = FetchType.LAZY)
    private List<BenefitLicense> benefitLiscenses = new ArrayList<>();

    @OneToMany(mappedBy = "benefit", fetch = FetchType.LAZY)
    private List<BenefitItem> benefitItems = new ArrayList<>();
}
