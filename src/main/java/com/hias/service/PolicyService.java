package com.hias.service;

import com.hias.exception.HIASException;
import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PolicyResponseDTO;

import java.util.List;

public interface PolicyService {
    List<PolicyResponseDTO> getAll();

    PolicyResponseDTO getDetail(Long policyNo);

    PolicyResponseDTO create(PolicyRequestDTO policyRequestDTO) throws HIASException;

    PolicyResponseDTO update(PolicyRequestDTO policyRequestDTO);

    PolicyResponseDTO delete(Long policyNo);
}
