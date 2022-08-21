package com.hias.model.request;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimUpdateRequestDTO implements Serializable {
    private Long businessAppraisalBy;
    private Long medicalAppraisalBy;
    private Long approvedBy;
    private Long paidBy;
}
