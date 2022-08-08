package com.hias.service.impl;

import com.hias.entity.Bank;
import com.hias.mapper.response.BankResponseDTOMapper;
import com.hias.model.response.BankResponseDTO;
import com.hias.repository.BankRepository;
import com.hias.service.BankService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankResponseDTOMapper bankResponseDTOMapper;

    @Override
    public List<BankResponseDTO> findAll() {
        List<BankResponseDTO> bankResponseDTOS = new ArrayList<>();
        List<Bank> banks = bankRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(banks)) {
            bankResponseDTOS = bankResponseDTOMapper.toDtoList(banks);
        }
        return bankResponseDTOS;
    }
}
