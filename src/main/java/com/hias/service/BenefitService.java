package com.hias.service;

import com.hias.exception.HIASException;
import com.hias.model.request.BenefitRequestDTO;
import com.hias.model.response.BenefitResponseDTO;

import java.util.List;

public interface BenefitService {

    BenefitResponseDTO findByBenefitNo(Long benefitNo);

    List<BenefitResponseDTO> findAll();

    BenefitResponseDTO create(BenefitRequestDTO benefitRequestDTO) throws HIASException;

    BenefitResponseDTO deleteByBenefitNo(Long benefitNo);

    BenefitResponseDTO update(BenefitRequestDTO benefitRequestDTO);
}
