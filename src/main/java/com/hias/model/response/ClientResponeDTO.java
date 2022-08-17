package com.hias.model.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClientResponeDTO implements Serializable {

    private Long clientNo;
    private String corporateID;
    private String clientName;
    private String email;
    private String phoneNumber;
    private String address;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<Long> BusinessSectorNos = new ArrayList<>();
}
