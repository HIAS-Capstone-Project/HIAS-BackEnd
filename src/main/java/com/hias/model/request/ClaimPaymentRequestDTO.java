package com.hias.model.request;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimPaymentRequestDTO implements Serializable {
    private Long claimNo;
    private BigDecimal paymentAmount;
    private String remark;
}
