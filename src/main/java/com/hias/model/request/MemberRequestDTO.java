package com.hias.model.request;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberRequestDTO implements Serializable {
    private Long memberNo;

    private String memberName;

    private String staffID;

    private String phoneNumber;

    private String email;

    private String address;
    private String bankAccountNo;

    private Long clientNo;

    private Long policyNo;

    private Long bankNo;

    @Override
    public String toString() {
        return "MemberRequestDTO{" +
                "memberNo=" + memberNo +
                ", memberName='" + memberName + '\'' +
                ", staffID='" + staffID + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", clientNo=" + clientNo +
                ", policyNo=" + policyNo +
                ", bankNo=" + bankNo +
                '}';
    }
}
