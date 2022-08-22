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
public class SingleLineChartResponseDTO {
    private List<StatisticDTO> statistics = new ArrayList<>();
    private String chartName;
}
