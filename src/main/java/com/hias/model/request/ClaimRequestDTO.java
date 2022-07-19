package com.hias.model.request;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimRequestDTO implements Serializable {
    private Long claimNo;
    private LocalDateTime visitDate;
    private LocalDateTime submittedDate;
    private LocalDateTime approvedDate;
    private LocalDateTime paymentDate;
    private String remark;
    private BigDecimal claimAmount;
    private Long serviceProviderNo;
    private Long memberNo;
    private Long benefitNo;
}
