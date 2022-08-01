package com.hias.entity;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLAIM_DOCUMENT", schema = "HIAS")
@Getter
@Setter
public class ClaimDocument extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLAIM_DOCUMENT_NO")
    private Long claimDocumentNo;

    @Column(name = "FILE_URL")
    private String fileUrl;

    @Column(name = "CLAIM_NO", insertable = false, updatable = false)
    private Long claimNo;

    @ManyToOne
    @JoinColumn(name = "CLAIM_NO", nullable = false)
    private Claim claim;

    @Column(name = "LISCENSE_NO", insertable = false, updatable = false)
    private Long liscenseNo;

    @ManyToOne
    @JoinColumn(name = "LISCENSE_NO", nullable = false)
    private Liscense liscense;
}
