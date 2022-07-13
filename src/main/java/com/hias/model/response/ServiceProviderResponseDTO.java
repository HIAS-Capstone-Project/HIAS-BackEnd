package com.hias.model.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ServiceProviderResponseDTO {
    private Long serviceProviderNo;

    private String serviceProviderID;

    private String serviceProviderName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String email;

    private String address;
}
