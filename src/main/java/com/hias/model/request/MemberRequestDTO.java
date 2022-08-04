package com.hias.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;

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
