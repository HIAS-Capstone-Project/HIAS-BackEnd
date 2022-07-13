package com.hias.model.response;

import lombok.*;
import java.io.Serializable;

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

    private String bankAccountNo;

    private Long clientNo;

    private Long policyNo;

    private Long bankNo;
}