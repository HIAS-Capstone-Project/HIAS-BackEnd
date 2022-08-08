package com.hias.model.request;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BenefitItemRequestDTO implements Serializable {

    private Long benefitItemNo;

    private Long benefitNo;

    private String benefitItemCode;

    private String benefitItemName;

    private String remark;
}
