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
public class BenefitResponseDTO implements Serializable {

    private Long benefitNo;

    private String benefitCode;

    private String benefitName;

    private String remark;

    private List<Long> benefitItemNos = new ArrayList<>();
}
