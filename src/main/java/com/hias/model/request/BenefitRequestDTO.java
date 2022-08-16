package com.hias.model.request;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BenefitRequestDTO implements Serializable {

    private Long benefitNo;

    private String benefitCode;

    private String benefitName;

    private String remark;

    private List<Long> licenseNos;
}
