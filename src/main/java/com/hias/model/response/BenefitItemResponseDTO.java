package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BenefitItemResponseDTO implements Serializable {

    private Long benefitItemNo;

    private Long benefitNo;

    private String benefitItemCode;

    private String benefitItemName;

    private String remark;
}
