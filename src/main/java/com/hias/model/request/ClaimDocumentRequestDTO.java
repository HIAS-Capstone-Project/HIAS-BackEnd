package com.hias.model.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClaimDocumentRequestDTO {
    private Long licenseNo;
    private List<String> fileUrls = new ArrayList<>();
    private List<String> fileNames = new ArrayList<>();
}
