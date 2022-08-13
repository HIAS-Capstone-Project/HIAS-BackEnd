package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LicenseResponseDTO implements Serializable {

    private Long licenseNo;

    private String licenseName;

    private String label;

    private String remark;

    private String fileUrl;
}
