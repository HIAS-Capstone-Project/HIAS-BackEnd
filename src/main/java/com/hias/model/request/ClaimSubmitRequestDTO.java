package com.hias.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ClaimSubmitRequestDTO implements Serializable {
    private Long claimNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime visitDate;

    private String remark;
    private BigDecimal claimAmount;
    private String description;
    private String medicalAddress;
    private Long serviceProviderNo;
    private Long memberNo;
    private Long benefitNo;
    private List<Long> licenseNos;
}
