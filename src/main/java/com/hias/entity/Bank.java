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
@Table(name = "BANK", schema = "HIAS")
@Getter
@Setter
public class Bank extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_NO")
    private Long bankNo;

    @Column(name = "BANK_NAME")
    private String bankName;

    @OneToMany(mappedBy = "bank")
    private List<Member> memberList = new ArrayList<>();
}
