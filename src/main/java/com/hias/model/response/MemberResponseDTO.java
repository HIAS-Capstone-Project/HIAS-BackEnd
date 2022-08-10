package com.hias.model.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberResponseDTO implements Serializable {
    private Long memberNo;

    private String memberName;

    private String staffID;

    private String phoneNumber;

    private String email;

    private String address;

    private String healthCardNo;

    private String bankAccountNo;

    private Long clientNo;

    private Long policyNo;

    private Long bankNo;

    private LocalDate dob;

    private LocalDate startDate;

    private LocalDate endDate;
}
