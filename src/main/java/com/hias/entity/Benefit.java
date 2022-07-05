package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BENEFIT", schema = "HIAS")
public class Benefit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BENEFIT_NO")
    private Long benefitNo;

    @Column(name = "BENEFIT_CODE")
    private String benefitCode;

    @Column(name = "BENEFIT_NAME")
    private String benefitName;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "benefit", fetch = FetchType.EAGER)
    private List<PolicyCoverage> policyCoverageList = new ArrayList<>();
}
