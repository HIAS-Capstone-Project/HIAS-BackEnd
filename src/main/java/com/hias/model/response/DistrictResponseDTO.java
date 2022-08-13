package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DistrictResponseDTO implements Serializable {

    private Long districtNo;

    private String districtName;

    private Long provinceNo;
}
