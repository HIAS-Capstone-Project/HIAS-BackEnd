package com.hias.service;

import com.hias.model.response.ChartResponseDTO;

import java.util.List;

public interface ChartService {
    ChartResponseDTO findMemberAgeChart(Long clientNo);

    ChartResponseDTO findMemberLocationChart(Long clientNo);

    ChartResponseDTO findMemberGenderChart(Long clientNo);

    List<ChartResponseDTO> findMemberOnboardChart(Long[] clientNos);

    ChartResponseDTO findClaimStatusChart(Long clientNo);
}
