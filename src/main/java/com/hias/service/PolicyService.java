package com.hias.service;

import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PolicyResponseDTO;

import java.util.List;

public interface PolicyService {
    List<PolicyResponseDTO> getAll();

    PolicyResponseDTO getDetail(Long policyNo);

    PolicyResponseDTO createPolicy(PolicyRequestDTO policyRequestDTO);

    PolicyResponseDTO updatePolicy(PolicyRequestDTO policyRequestDTO);

    PolicyResponseDTO deletePolicy(Long policyNo);
}
