package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BENEFIT_LICENSE", schema = "HIAS")
@Getter
@Setter
public class BenefitLicense extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BENEFIT_LICENSE_NO")
    private Long benefitLicenseNo;

    @Column(name = "BENEFIT_NO", updatable = false, insertable = false)
    private Long benefitNo;

    @ManyToOne
    @JoinColumn(name = "BENEFIT_NO", nullable = false)
    private Benefit benefit;

    @Column(name = "LICENSE_NO", updatable = false, insertable = false)
    private Long licenseNo;

    @ManyToOne
    @JoinColumn(name = "LICENSE_NO", nullable = false)
    private License license;
}
