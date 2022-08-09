package com.hias.model.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiscenseResponseDTO implements Serializable {

    private Long liscenseNo;

    private String liscenseName;

    private String label;

    private String remark;

    private String fileUrl;
}
