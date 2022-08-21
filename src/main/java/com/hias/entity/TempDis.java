package com.hias.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempDis {
    @JsonProperty("name")
    String name;
}
