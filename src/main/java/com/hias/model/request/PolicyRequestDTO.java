package com.hias.model.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PolicyRequestDTO {
    private Long clientNo;
    private Long policyNo;
    private String policyCode;
    private String policyName;
    private String remark;

}
