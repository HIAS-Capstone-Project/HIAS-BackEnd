package com.hias.model.request;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClientRequestDTO implements Serializable {
    private Long clientNo;
    private String corporateID;
    private String clientName;
    private String email;
    private String phoneNumber;
    private String address;
    private List<Long> businessSectorNos = new ArrayList<>();
    private List<Long> employeeNos = new ArrayList<>();
}
