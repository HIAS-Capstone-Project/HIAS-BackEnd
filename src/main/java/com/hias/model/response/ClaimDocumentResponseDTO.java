package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimDocumentResponseDTO implements Serializable {
    private Long claimDocumentNo;
    private Long claimNo;
    private String fileUrl;
    private String pathFile;
    private String fileName;
    private String originalFileName;

    private Long licenseNo;
    private String licenseName;
    private String label;
}
