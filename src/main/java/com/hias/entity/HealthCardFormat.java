package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HEALTH_CARD_FORMAT", schema = "HIAS")
@Getter
@Setter
public class HealthCardFormat extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HEALTH_CARD_FORMAT_NO")
    private Long healthCardFormatNo;

    @Column(name = "PREFIX", unique = true)
    private String prefix;

    @Column(name = "CLIENT_NO", insertable = false, updatable = false)
    private Long clientNo;

    @ManyToOne
    @JoinColumn(name = "CLIENT_NO", nullable = false)
    private Client client;
}
