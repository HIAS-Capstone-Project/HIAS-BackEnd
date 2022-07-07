package com.hias.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNTANT", schema = "HIAS")
@Getter
@Setter
public class Accountant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNTANT_NO")
    private Long accountNo;

    @Column(name = "EMPLOYEE_NO", nullable = false, unique = true)
    private Long employeeNo;

    @OneToMany(mappedBy = "accountant")
    private List<ClaimPayment> claimPaymentList = new ArrayList<>();
}
