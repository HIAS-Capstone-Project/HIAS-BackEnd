package com.hias.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProvinceResponseDTO implements Serializable {

    private Long provinceNo;

    private String provinceName;

    private List<DistrictResponseDTO> districtResponseDTOS;
}
