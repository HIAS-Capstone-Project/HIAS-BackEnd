package com.hias.service;

import com.hias.model.response.ChartResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChartService {
    ChartResponseDTO findMemberAgeChart(Long clientNo);

    ChartResponseDTO findMemberLocationChart(Long clientNo);

    ChartResponseDTO findMemberGenderChart(Long clientNo);

    List<ChartResponseDTO> findMemberOnboardChart(Long[] clientNos);
}
