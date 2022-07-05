package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER", schema = "HIAS")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_NO")
    private Long memberNo;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "STAFF_ID")
    private String staffID;

    @Column(name = "PHONE_NUMBER", unique = true)
    private String phoneNumber;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "REMARK")
    private String remark;

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
}
