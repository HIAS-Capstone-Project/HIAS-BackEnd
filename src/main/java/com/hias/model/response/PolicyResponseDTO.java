package com.hias.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PolicyResponseDTO implements Serializable {
    private Long policyNo;
    private String policyCode;
    private String policyName;
    private String remark;
    private Long clientNo;
    private List<Long> benefitNos = new ArrayList<>();
}
