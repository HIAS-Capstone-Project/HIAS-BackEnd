package com.hias.service;

import com.hias.exception.HIASException;
import com.hias.model.request.BenefitItemRequestDTO;
import com.hias.model.response.BenefitItemResponseDTO;
import com.hias.model.response.PagingResponseModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BenefitItemService {

    BenefitItemResponseDTO findByBenefitItemNo(Long benefitItemNo);

    List<BenefitItemResponseDTO> findAll();

    PagingResponseModel<BenefitItemResponseDTO> search(String searchValue, Pageable pageable);

    BenefitItemResponseDTO create(BenefitItemRequestDTO benefitItemRequestDTO) throws HIASException;

    BenefitItemResponseDTO update(BenefitItemRequestDTO benefitItemRequestDTO);

    BenefitItemResponseDTO deleteByBenefitItemNo(Long benefitItemNo);
}
