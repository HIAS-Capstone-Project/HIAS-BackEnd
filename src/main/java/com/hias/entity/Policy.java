package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POLICY", schema = "HIAS")
@Getter
@Setter
public class Policy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLICY_NO")
    private Long policyNo;

    @Column(name = "POLICY_CODE")
    private String policyCode;

    @Column(name = "POLICY_NAME")
    private String policyName;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "CLIENT_NO", insertable = false, updatable = false)
    private Long clientNo;

    @ManyToOne
    @JoinColumn(name = "CLIENT_NO", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "policy", fetch = FetchType.EAGER)
    private List<Member> memberList = new ArrayList<>();

    @OneToMany(mappedBy = "policy", fetch = FetchType.EAGER)
    private List<PolicyCoverage> policyCoverageList = new ArrayList<>();
}
