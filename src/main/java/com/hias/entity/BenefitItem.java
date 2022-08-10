package com.hias.entity;


import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BENEFIT_ITEM", schema = "HIAS")
@Getter
@Setter
public class BenefitItem extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BENEFIT_ITEM_NO")
    private Long benefitItemNo;

    @Column(name = "BENEFIT_ITEM_CODE")
    private String benefitItemCode;

    @Column(name = "BENEFIT_ITEM_NAME")
    private String benefitItemName;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "BUDGET_AMOUNT")
    private BigDecimal budgetAmount;

    @Column(name = "BENEFIT_NO", insertable = false, updatable = false)
    private Long benefitNo;

    @ManyToOne
    @JoinColumn(name = "BENEFIT_NO", nullable = false)
    private Benefit benefit;

//    @Column(name = "BENEFIT_ITEM_TYPE_NO", insertable = false, updatable = false)
//    private Long benefitItemTypeNo;
//
//    @ManyToOne
//    @JoinColumn(name = "BENEFIT_ITEM_TYPE_NO", nullable = false)
//    private BenefitItemType benefitItemType;

    @OneToMany(mappedBy = "benefitItem", fetch = FetchType.LAZY)
    private List<Claim> claimList = new ArrayList<>();
}
