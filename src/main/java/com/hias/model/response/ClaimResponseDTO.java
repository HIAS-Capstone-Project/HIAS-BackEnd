package com.hias.model.response;

import com.hias.constant.RecordSource;
import com.hias.constant.StatusCode;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimResponseDTO implements Serializable {
    private Long claimNo;
    private String claimID;
    private LocalDateTime visitDate;
    private LocalDateTime submittedDate;
    private LocalDateTime approvedDate;
    private LocalDateTime paymentDate;
    private LocalDateTime medicalAppraisalDate;
    private LocalDateTime businessAppraisalDate;
    private LocalDateTime admissionFromDate;
    private LocalDateTime admissionToDate;
    private String remark;
    private String medicalAddress;
    private String description;
    private String rejectReason;
    private String returnReason;
    private BigDecimal claimAmount;
    private BigDecimal paymentAmount;
    private Long serviceProviderNo;
    private Long memberNo;
    private Long benefitNo;
    private Long businessAppraisalBy;
    private Long medicalAppraisalBy;
    private StatusCode statusCode;
    private RecordSource recordSource;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;

    private MemberResponseDTO memberResponseDTO;
    private ClientResponseDTO clientResponseDTO;
    private PolicyResponseDTO policyResponseDTO;
    private BenefitResponseDTO benefitResponseDTO;
    private ServiceProviderResponseDTO serviceProviderResponseDTO;
    private EmployeeResponseDTO businessAppraisal;
    private EmployeeResponseDTO medicalAppraisal;
    private EmployeeResponseDTO approver;
    private EmployeeResponseDTO accountant;

    private List<ClaimDocumentResponseDTO> claimDocumentResponseDTOS;
    private List<ClaimRemarkHistoryResponseDTO> claimRemarkHistoryResponseDTOS;
}
