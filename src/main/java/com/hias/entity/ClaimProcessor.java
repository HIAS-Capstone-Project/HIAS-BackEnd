package com.hias.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLAIM_PROCESSOR", schema = "HIAS")
@Getter
@Setter
public class ClaimProcessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLAIM_PROCESSOR_NO")
    private Long claimProcessorNo;

    @Column(name = "EMPLOYEE_NO", nullable = false, unique = true)
    private Long employeeNo;

    @OneToMany(mappedBy = "claim")
    private List<ClaimRequestHistory> claimRequestHistories = new ArrayList<>();
}
