package com.hias.service.impl;

import com.hias.constant.ErrorMessageCode;
import com.hias.entity.Benefit;
import com.hias.exception.HIASException;
import com.hias.mapper.request.BenefitRequestDTOMapper;
import com.hias.mapper.response.BenefitResponseDTOMapper;
import com.hias.model.request.BenefitRequestDTO;
import com.hias.model.response.BenefitResponseDTO;
import com.hias.repository.BenefitRepository;
import com.hias.service.BenefitService;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.BenefitValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;
    private final BenefitRequestDTOMapper benefitRequestDTOMapper;
    private final BenefitResponseDTOMapper benefitResponseDTOMapper;
    private final BenefitValidator benefitValidator;
    private final MessageUtils messageUtils;

    @Override
    public BenefitResponseDTO findByBenefitNo(Long benefitNo) {
        BenefitResponseDTO benefitResponseDTO = new BenefitResponseDTO();
        Optional<Benefit> benefitOptional = benefitRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        if (!benefitOptional.isPresent()) {
            log.warn("[findByBenefitNo] Cannot found benefit with benefit no : {}", benefitNo);
            return benefitResponseDTO;
        }
        log.info("[findByBenefitNo] Found benefit with benefit no : {}", benefitNo);
        return benefitResponseDTOMapper.toDto(benefitOptional.get());
    }

    @Override
    public List<BenefitResponseDTO> findAll() {
        List<BenefitResponseDTO> benefitResponseDTOS = new ArrayList<>();
        List<Benefit> benefits = benefitRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(benefits)) {
            log.info("[findAll] Found {} benefits.", benefits.size());
            benefitResponseDTOS = benefitResponseDTOMapper.toDtoList(benefits);
        }
        return benefitResponseDTOS;
    }

    @Override
    @Transactional
    public BenefitResponseDTO create(BenefitRequestDTO benefitRequestDTO) throws HIASException {
        String benefitCode = benefitRequestDTO.getBenefitCode();
        log.info("[create] Start create new benefit.");
        if (benefitValidator.isBenefitCodeExistance(benefitCode)) {
            throw HIASException.buildHIASException(
                    messageUtils.getMessage(ErrorMessageCode.BENEFIT_CODE_EXISTENCE)
                    , HttpStatus.NOT_ACCEPTABLE);
        }
        BenefitResponseDTO benefitResponseDTO;
        Benefit benefitCreated = benefitRepository.save(benefitRequestDTOMapper.toEntity(benefitRequestDTO));
        benefitResponseDTO = benefitResponseDTOMapper.toDto(benefitCreated);
        log.info("[create] End create new benefit with benefit no : {}", benefitResponseDTO.getBenefitNo());
        return benefitResponseDTO;
    }

    @Override
    @Transactional
    public BenefitResponseDTO deleteByBenefitNo(Long benefitNo) {
        BenefitResponseDTO benefitResponseDTO = new BenefitResponseDTO();
        Optional<Benefit> benefitOptional = benefitRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        if (!benefitOptional.isPresent()) {
            log.info("[deleteByBenefitNo] Cannot found benefit with benefitNo : {} in the system.", benefitNo);
        } else {
            Benefit benefit = benefitOptional.get();
            benefit.setDeleted(Boolean.TRUE);
            benefitResponseDTO = benefitResponseDTOMapper.toDto(benefitRepository.save(benefit));
            log.info("[deleteByBenefitNo] Delete benefit with benefitNo : {} in the system.", benefitNo);
        }
        return benefitResponseDTO;
    }

    @Override
    @Transactional
    public BenefitResponseDTO update(BenefitRequestDTO benefitRequestDTO) {
        BenefitResponseDTO benefitResponseDTO = new BenefitResponseDTO();
        Long benefitNo = benefitRequestDTO.getBenefitNo();
        Optional<Benefit> benefitOptional = benefitRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        if (!benefitOptional.isPresent()) {
            log.info("[update] Cannot found benefit with benefitNo : {} in the system.", benefitNo);
        } else {
            Benefit benefit = benefitRequestDTOMapper.toEntity(benefitRequestDTO);
            benefitResponseDTO = benefitResponseDTOMapper.toDto(benefitRepository.save(benefit));
            log.info("[update] Updated benefit with benefitNo : {} in the system.", benefitNo);
        }
        return benefitResponseDTO;
    }
}
