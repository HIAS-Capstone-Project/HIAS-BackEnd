package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLAIM_PAYMENT", schema = "HIAS")
@Getter
@Setter
public class ClaimPayment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLAIM_PAYMENT_NO")
    private Long claimPaymentNo;

    @Column(name = "CLAIM_NO", insertable = false, updatable = false)
    private Long claimNo;

    @ManyToOne
    @JoinColumn(name = "CLAIM_NO", nullable = false)
    private Claim claim;

    @Column(name = "ACCOUNTANT_NO", insertable = false, updatable = false)
    private Long accountNo;

    @ManyToOne
    @JoinColumn(name = "ACCOUNTANT_NO", nullable = false)
    private Accountant accountant;

    @Column(name = "PAYMENT_AMOUNT")
    private BigDecimal paymentAmount;

    @Column(name = "PAID_BY")
    private String paidBy;
}
