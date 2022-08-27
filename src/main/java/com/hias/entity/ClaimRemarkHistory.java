package com.hias.entity;

import com.hias.constant.ActionType;
import com.hias.constant.StatusCode;
import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLAIM_REMARK_HISTORY", schema = "HIAS")
@Getter
@Setter
public class ClaimRemarkHistory extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLAIM_REMARK_HISTORY_NO")
    private Long claimRequestHistoryNo;

    @Column(name = "EMPLOYEE_NO")
    private Long employeeNo;

    @Column(name = "CLAIM_NO", insertable = false, updatable = false)
    private Long claimNo;

    @ManyToOne
    @JoinColumn(name = "CLAIM_NO", nullable = false)
    private Claim claim;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "FROM_STATUS_CODE")
    private StatusCode fromStatusCode;

    @Column(name = "TO_STATUS_CODE")
    private StatusCode toStatusCode;

    @Column(name = "ACTION_TYPE")
    private ActionType actionType;
}
