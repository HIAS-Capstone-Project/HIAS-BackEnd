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
@Table(name = "LICENSE", schema = "HIAS")
@Getter
@Setter
public class License extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LICENSE_NO")
    private Long licenseNo;

    @Column(name = "LICENSE_NAME")
    private String licenseName;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "FILE_URL")
    private String fileUrl;

    @OneToMany(mappedBy = "license")
    private List<BenefitLicense> benefitLicenses = new ArrayList<>();

    @OneToMany(mappedBy = "license")
    private List<ClaimDocument> claimDocuments = new ArrayList<>();
}
