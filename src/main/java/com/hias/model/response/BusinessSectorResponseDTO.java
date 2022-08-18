package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BusinessSectorResponseDTO implements Serializable {
    private Long businessSectorNo;
    private String businessSectorName;
}
