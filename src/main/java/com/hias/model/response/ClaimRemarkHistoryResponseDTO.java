package com.hias.model.response;

import com.hias.constant.ActionType;
import com.hias.constant.StatusCode;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimRemarkHistoryResponseDTO implements Serializable {

    private Long claimRequestHistoryNo;
    private Long employeeNo;
    private EmployeeResponseDTO employeeResponseDTO;
    private Long claimNo;
    private String remark;
    private StatusCode fromStatusCode;
    private StatusCode toStatusCode;
    private ActionType actionType;
}
