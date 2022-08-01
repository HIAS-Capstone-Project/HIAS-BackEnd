package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BENEFIT_LISCENSE", schema = "HIAS")
@Getter
@Setter
public class BenefitLiscense extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BENEFIT_LISCENSE_NO")
    private Long benefitLiscenseNo;

    @Column(name = "BENEFIT_NO", updatable = false, insertable = false)
    private Long benefitNo;

    @ManyToOne
    @JoinColumn(name = "BENEFIT_NO", nullable = false)
    private Benefit benefit;

    @Column(name = "LISCENSE_NO", updatable = false, insertable = false)
    private Long liscenseNo;

    @ManyToOne
    @JoinColumn(name = "LISCENSE_NO", nullable = false)
    private Liscense liscense;
}
