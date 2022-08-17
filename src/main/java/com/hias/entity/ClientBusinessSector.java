package com.hias.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT_BUSINESS_SECTOR", schema = "HIAS")
@Getter
@Setter
public class ClientBusinessSector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_BUSINESS_SECTOR_NO")
    private Long clientBusinessSectorNo;

    @Column(name = "BUSINESS_SECTOR_NO", insertable = false, updatable = false)
    private Long businessSectorNo;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_SECTOR_NO", nullable = false)
    private BusinessSector businessSector;

    @Column(name = "CLIENT_NO", insertable = false, updatable = false)
    private Long clientNo;

    @ManyToOne
    @JoinColumn(name = "CLIENT_NO", nullable = false)
    private Client client;
}
