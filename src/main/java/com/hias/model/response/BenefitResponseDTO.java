package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BenefitResponseDTO implements Serializable {

    private Long benefitNo;

    private String benefitCode;

    private String benefitName;

    private String remark;
}
