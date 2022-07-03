package com.hias.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenResponseDTO implements Serializable {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("roles")
    private List<String> roles;

    @JsonProperty("have_not_permission")
    private boolean haveNotPermission;

    @JsonProperty("expiry_date")
    private Date expiryDate;
}
