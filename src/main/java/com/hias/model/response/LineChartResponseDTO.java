package com.hias.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LineChartResponseDTO {
    List<SingleLineChartResponseDTO> lines = new ArrayList<>();
    String chartType;
    private String[] roles;
}
