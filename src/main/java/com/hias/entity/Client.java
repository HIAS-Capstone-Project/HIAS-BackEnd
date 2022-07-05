package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT", schema = "HIAS")
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_NO")
    private Long clientNo;

    @Column(name = "CORPORATE_ID", unique = true)
    private Long corporateID;

    @Column(name = "CLIENT_NAME")
    private String name;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Policy> policyList = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Member> memberList = new ArrayList<>();
}
