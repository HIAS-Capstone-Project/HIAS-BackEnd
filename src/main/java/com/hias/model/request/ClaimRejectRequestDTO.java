package com.hias.model.request;

import com.hias.constant.StatusReasonCode;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimRejectRequestDTO implements Serializable {
    private Long claimNo;
    private String rejectReason;
    private StatusReasonCode statusReasonCode;
}
