package com.hias.service;

import com.hias.exception.HIASException;
import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.model.response.PolicyResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PolicyService {
    List<PolicyResponseDTO> getAll();

    PagingResponseModel<PolicyResponseDTO> search(String searchValue, Pageable pageable);

    PolicyResponseDTO getDetail(Long policyNo);

    PolicyResponseDTO create(PolicyRequestDTO policyRequestDTO) throws HIASException;

    PolicyResponseDTO update(PolicyRequestDTO policyRequestDTO);

    PolicyResponseDTO delete(Long policyNo);
}
