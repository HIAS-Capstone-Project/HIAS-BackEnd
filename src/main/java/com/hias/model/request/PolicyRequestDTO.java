package com.hias.model.request;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PolicyRequestDTO implements Serializable {
    private Long clientNo;
    private Long policyNo;
    private String policyCode;
    private String policyName;
    private String remark;
    private List<Long> benefitNos;
}
