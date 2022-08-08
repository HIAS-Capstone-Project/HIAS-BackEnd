package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BankResponseDTO implements Serializable {

    private Long bankNo;

    private String bankName;
}
