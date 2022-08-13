package com.hias.service;

import com.hias.model.response.ChartResponseDTO;

public interface ChartService {
    ChartResponseDTO findMemberAgeChart(Long clientNo);

    ChartResponseDTO findMemberLocationChart(Long clientNo);

    ChartResponseDTO findMemberGenderChart(Long clientNo);
}
