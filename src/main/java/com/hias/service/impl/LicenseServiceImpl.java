package com.hias.service.impl;

import com.hias.entity.BenefitLicense;
import com.hias.entity.License;
import com.hias.mapper.response.LiscenseResponseDTOMapper;
import com.hias.model.response.LicenseResponseDTO;
import com.hias.repository.BenefitLiscenseRepository;
import com.hias.repository.LiscenseRepository;
import com.hias.service.LicenseService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LicenseServiceImpl implements LicenseService {


    private final LiscenseRepository liscenseRepository;
    private final BenefitLiscenseRepository benefitLiscenseRepository;
    private final LiscenseResponseDTOMapper liscenseResponseDTOMapper;

    @Override
    public List<LicenseResponseDTO> findAll() {
        List<LicenseResponseDTO> licenseResponseDTOS = new ArrayList<>();
        List<License> liscenses = liscenseRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(liscenses)) {
            licenseResponseDTOS = liscenseResponseDTOMapper.toDtoList(liscenses);
        }
        return licenseResponseDTOS;
    }

    @Override
    public List<LicenseResponseDTO> findAllByBenefitNo(Long benefitNo) {
        List<LicenseResponseDTO> licenseResponseDTOS = new ArrayList<>();
        List<BenefitLicense> benefitLicenses = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        if (CollectionUtils.isEmpty(benefitLicenses)) {
            return licenseResponseDTOS;
        }
        List<License> liscenses = benefitLicenses.stream().map(o -> o.getLicense()).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(liscenses)) {
            licenseResponseDTOS = liscenseResponseDTOMapper.toDtoList(liscenses);
        }
        return licenseResponseDTOS;
    }
}
