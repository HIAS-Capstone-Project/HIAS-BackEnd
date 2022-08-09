package com.hias.service.impl;

import com.hias.entity.BenefitLiscense;
import com.hias.entity.Liscense;
import com.hias.mapper.response.LiscenseResponseDTOMapper;
import com.hias.model.response.LiscenseResponseDTO;
import com.hias.repository.BenefitLiscenseRepository;
import com.hias.repository.LiscenseRepository;
import com.hias.service.LiscenseService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LiscenseServiceImpl implements LiscenseService {


    private final LiscenseRepository liscenseRepository;
    private final BenefitLiscenseRepository benefitLiscenseRepository;
    private final LiscenseResponseDTOMapper liscenseResponseDTOMapper;

    @Override
    public List<LiscenseResponseDTO> findAll() {
        List<LiscenseResponseDTO> liscenseResponseDTOS = new ArrayList<>();
        List<Liscense> liscenses = liscenseRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(liscenses)) {
            liscenseResponseDTOS = liscenseResponseDTOMapper.toDtoList(liscenses);
        }
        return liscenseResponseDTOS;
    }

    @Override
    public List<LiscenseResponseDTO> findAllByBenefitNo(Long benefitNo) {
        List<LiscenseResponseDTO> liscenseResponseDTOS = new ArrayList<>();
        List<BenefitLiscense> benefitLiscenses = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        if (CollectionUtils.isEmpty(benefitLiscenses)) {
            return liscenseResponseDTOS;
        }
        List<Liscense> liscenses = benefitLiscenses.stream().map(o -> o.getLiscense()).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(liscenses)) {
            liscenseResponseDTOS = liscenseResponseDTOMapper.toDtoList(liscenses);
        }
        return liscenseResponseDTOS;
    }
}
