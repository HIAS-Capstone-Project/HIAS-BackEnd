package com.hias.service;

import com.hias.model.response.LiscenseResponseDTO;

import java.util.List;

public interface LiscenseService {

    List<LiscenseResponseDTO> findAll();

    List<LiscenseResponseDTO> findAllByBenefitNo(Long benefitNo);

}
