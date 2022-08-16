package com.hias.entity;

import com.hias.constant.RecordSource;
import com.hias.entity.base.BaseEntity;
import com.hias.jpa_converter.RecordSourceConverter;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLAIM", schema = "HIAS")
@Getter
@Setter
public class Claim extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLAIM_NO")
    private Long claimNo;

    @Column(name = "CLAIM_ID", insertable = false, updatable = false)
    private String claimID;

    @Column(name = "VISIT_DATE")
    private LocalDateTime visitDate;

    @Column(name = "ADMISSION_FROM_DATE")
    private LocalDateTime admissionFromDate;

    @Column(name = "ADMISSION_TO_DATE")
    private LocalDateTime admissionToDate;

    @Column(name = "SUBMITTED_DATE")
    private LocalDateTime submittedDate;

    @Column(name = "MEDICAL_EXAMINATION_DATE")
    private LocalDateTime medicalExaminationDate;

    @Column(name = "BUSINESS_EXAMINATION_DATE")
    private LocalDateTime businessExaminationDate;

    @Column(name = "APPROVED_DATE")
    private LocalDateTime approvedDate;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MEDICAL_ADDRESS")
    private String medicalAddress;

    @Column(name = "CLAIM_AMOUNT")
    private BigDecimal claimAmount;

    @Column(name = "SERVICE_PROVIDER_NO")
    private Long serviceProviderNo;

    @Column(name = "BENEFIT_NO")
    private Long benefitNo;

    @Column(name = "BUSINESS_APPRAISAL_BY")
    private Long businessAppraisalBy;

    @Column(name = "MEDICAL_APPRAISAL_BY")
    private Long medicalAppraisalBy;

    @Column(name = "RECORD_SOURCE")
    @Convert(converter = RecordSourceConverter.class)
    private RecordSource recordSource;

    @Column(name = "MEMBER_NO", insertable = false, updatable = false)
    private Long memberNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "claim", fetch = FetchType.LAZY)
    private List<ClaimRequestHistory> claimRequestHistories = new ArrayList<>();

    @OneToMany(mappedBy = "claim", fetch = FetchType.LAZY)
    private List<ClaimPayment> claimPaymentList = new ArrayList<>();
}
