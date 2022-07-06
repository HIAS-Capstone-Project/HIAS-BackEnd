package com.hias.model.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClientResponeDTO implements Serializable {

    private Long clientNo;

    private String corporateID;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
