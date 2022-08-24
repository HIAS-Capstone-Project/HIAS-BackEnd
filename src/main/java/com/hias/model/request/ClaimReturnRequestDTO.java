package com.hias.model.request;

import com.hias.constant.StatusReasonCode;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimReturnRequestDTO implements Serializable {
    private Long claimNo;
    private String returnReason;
    private StatusReasonCode returnReasonCode;
}
