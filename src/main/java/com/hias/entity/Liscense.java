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
@Table(name = "LISCENSE", schema = "HIAS")
@Getter
@Setter
public class Liscense extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LISCENSE_NO")
    private Long liscenseNo;

    @Column(name = "LISCENSE_NAME")
    private String liscenseName;

    @Column(name = "LABEL")
    private String label;

    @OneToMany(mappedBy = "liscense")
    private List<BenefitLiscense> benefitLiscenses = new ArrayList<>();

    @OneToMany(mappedBy = "liscense")
    private List<ClaimDocument> claimDocuments = new ArrayList<>();
}
