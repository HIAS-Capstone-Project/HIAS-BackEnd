package com.hias.service;

import com.hias.model.response.LicenseResponseDTO;

import java.util.List;

public interface LicenseService {

    List<LicenseResponseDTO> findAll();

    List<LicenseResponseDTO> findAllByBenefitNo(Long benefitNo);

}
