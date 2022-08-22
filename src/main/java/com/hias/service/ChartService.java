package com.hias.service;

import com.hias.model.response.ChartResponseDTO;
import com.hias.model.response.LineChartResponseDTO;
import com.hias.model.response.StatisticDTO;

import java.time.LocalDate;

public interface ChartService {
    ChartResponseDTO findMemberAgeChart(Long clientNo);

    ChartResponseDTO findMemberLocationChart(Long clientNo);

    ChartResponseDTO findMemberGenderChart(Long clientNo);

    LineChartResponseDTO findMemberOnboardChart(Long[] clientNos);

    ChartResponseDTO findClaimStatusChart(Long clientNo);

    ChartResponseDTO findPolicyByUsage(LocalDate startDate, LocalDate endDate);

    ChartResponseDTO findBusinessSectorChart();

    ChartResponseDTO findClaimBySpecialStatus(LocalDate startDate, LocalDate endDate);

    List<StatisticDTO> findAll();
}
