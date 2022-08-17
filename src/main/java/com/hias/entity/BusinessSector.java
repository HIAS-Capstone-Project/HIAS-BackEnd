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
@Table(name = "BUSINESS_SECTOR", schema = "HIAS")
@Getter
@Setter
public class BusinessSector extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUSINESS_SECTOR_NO")
    private Long businessSectorNo;

    @Column(name = "BUSINESS_SECTOR_NAME")
    private String businessSectorName;

    @OneToMany(mappedBy = "businessSector", fetch = FetchType.LAZY)
    private List<ClientBusinessSector> clientBusinessSectors = new ArrayList<>();
}
