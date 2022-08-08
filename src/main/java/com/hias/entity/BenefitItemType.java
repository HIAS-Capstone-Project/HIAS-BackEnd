package com.hias.entity;


import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BENEFIT_ITEM_TYPE", schema = "HIAS")
@Getter
@Setter
public class BenefitItemType extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BENEFIT_ITEM_TYPE_NO")
    private Long benefitItemTypeNo;

    @Column(name = "BENEFIT_ITEM_TYPE_CODE")
    private String benefitItemTypeCode;

    @Column(name = "BENEFIT_ITEM_TYPE_NAME")
    private String benefitItemTypeName;

    @Column(name = "REMARK")
    private String remark;

//    @OneToMany(mappedBy = "benefitItemType", fetch = FetchType.LAZY)
//    private List<BenefitItem> benefitItems = new ArrayList<>();
}
