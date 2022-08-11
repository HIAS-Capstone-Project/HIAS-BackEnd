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
@Table(name = "DISTRICT", schema = "HIAS")
@Getter
@Setter
public class District extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISTRICT_NO")
    private Long districtNo;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @Column(name = "PROVINCE_NO", insertable = false, updatable = false)
    private Long provinceNo;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_NO", nullable = false)
    private Province province;

    @OneToMany(mappedBy = "district")
    private List<Member> memberList = new ArrayList<>();
}
