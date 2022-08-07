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
@Table(name = "PROVINCE", schema = "HIAS")
@Getter
@Setter
public class Province extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROVINCE_NO")
    private Long provinceNo;

    @Column(name = "PROVINCE_NAME")
    private String provinceName;

    @OneToMany(mappedBy = "province")
    private List<District> districts = new ArrayList<>();
}
