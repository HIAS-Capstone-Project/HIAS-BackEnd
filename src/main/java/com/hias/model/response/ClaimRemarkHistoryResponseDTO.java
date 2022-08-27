package com.hias.model.response;

import com.hias.constant.ActionType;
import com.hias.constant.StatusCode;

import java.io.Serializable;

public class ClaimRemarkHistoryResponseDTO implements Serializable {

    private Long claimRequestHistoryNo;
    private Long employeeNo;
    private Long claimNo;
    private String remark;
    private StatusCode fromStatusCode;
    private StatusCode toStatusCode;
    private ActionType actionType;
}
