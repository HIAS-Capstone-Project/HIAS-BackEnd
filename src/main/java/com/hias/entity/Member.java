package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER", schema = "HIAS")
@Getter
@Setter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_NO")
    private Long memberNo;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "STAFF_ID")
    private String staffID;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "BANK_ACCOUNT_NO")
    private String bankAccountNo;

    @Column(name = "HEALTH_CARD_NO")
    private String healthCardNo;

    @Column(name = "CLIENT_NO", insertable = false, updatable = false)
    private Long clientNo;

    @ManyToOne
    @JoinColumn(name = "CLIENT_NO", nullable = false)
    private Client client;

    @Column(name = "POLICY_NO", insertable = false, updatable = false)
    private Long policyNo;

    @ManyToOne
    @JoinColumn(name = "POLICY_NO", nullable = false)
    private Policy policy;

    @Column(name = "BANK_NO", insertable = false, updatable = false)
    private Long bankNo;

    @ManyToOne
    @JoinColumn(name = "BANK_NO", nullable = false)
    private Bank bank;

    @OneToMany(mappedBy = "member")
    private List<Claim> claimList = new ArrayList<>();
}
