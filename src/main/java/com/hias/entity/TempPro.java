package com.hias.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TempPro {
    @JsonProperty("name")
    String name;

    @JsonProperty("districts")
    List<TempDis> districts = new ArrayList<>();
}
