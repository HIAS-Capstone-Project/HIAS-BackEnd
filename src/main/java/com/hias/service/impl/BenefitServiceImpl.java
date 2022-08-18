package com.hias.service.impl;

import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.entity.*;
import com.hias.exception.HIASException;
import com.hias.mapper.request.BenefitRequestDTOMapper;
import com.hias.mapper.response.BenefitItemResponseDTOMapper;
import com.hias.mapper.response.BenefitResponseDTOMapper;
import com.hias.mapper.response.LicenseResponseDTOMapper;
import com.hias.model.request.BenefitRequestDTO;
import com.hias.model.response.BenefitResponseDTO;
import com.hias.model.response.LicenseResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.*;
import com.hias.service.BenefitService;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.BenefitValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;
    private final BenefitRequestDTOMapper benefitRequestDTOMapper;
    private final BenefitResponseDTOMapper benefitResponseDTOMapper;
    private final BenefitValidator benefitValidator;
    private final MessageUtils messageUtils;
    private final BenefitItemRepository benefitItemRepository;
    private final PolicyCoverageRepository policyCoverageRepository;
    private final BenefitLiscenseRepository benefitLiscenseRepository;
    private final LicenseRepository licenseRepository;
    private final MemberRepository memberRepository;
    private final BenefitItemResponseDTOMapper benefitItemResponseDTOMapper;
    private final LicenseResponseDTOMapper licenseResponseDTOMapper;


    @Override
    public BenefitResponseDTO findByBenefitNo(Long benefitNo) {
        BenefitResponseDTO benefitResponseDTO = new BenefitResponseDTO();
        Optional<Benefit> benefitOptional = benefitRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        if (!benefitOptional.isPresent()) {
            log.warn("[findByBenefitNo] Cannot found benefit with benefit no : {}", benefitNo);
            return benefitResponseDTO;
        }
        benefitResponseDTO = benefitResponseDTOMapper.toDto(benefitOptional.get());
        List<BenefitLicense> benefitLicenses = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        List<License> licenses = benefitLicenses.stream().map(b -> b.getLicense()).collect(Collectors.toList());
        List<LicenseResponseDTO> licenseResponseDTOS = licenseResponseDTOMapper.toDtoList(licenses);
        benefitResponseDTO.setLicenseResponseDTOS(licenseResponseDTOS);
        log.info("[findByBenefitNo] Found benefit with benefit no : {}", benefitNo);
        return benefitResponseDTO;
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
    public List<BenefitResponseDTO> findByMemberNo(Long memberNo) {
        List<BenefitResponseDTO> benefitResponseDTOS = new ArrayList<>();
        Optional<Member> memberOptional = memberRepository.findByMemberNoAndIsDeletedIsFalse(memberNo);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            List<PolicyCoverage> policyCoverages = policyCoverageRepository.findAllByPolicyNoAndIsDeletedIsFalse(member.getPolicyNo());
            List<Benefit> benefits = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(policyCoverages)) {
                benefits = policyCoverages
                        .stream().map(p -> p.getBenefit()).collect(Collectors.toList());
            }
            List<BenefitLicense> benefitLicenses;
            List<License> licenses;
            List<BenefitItem> benefitItems;
            Long benefitNo;
            for (Benefit benefit : benefits) {
                benefitNo = benefit.getBenefitNo();

                //set benefit items
                BenefitResponseDTO benefitResponseDTO = benefitResponseDTOMapper.toDto(benefit);
                benefitItems = benefitItemRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
                benefitResponseDTO.setBenefitItemNos(benefitItems.stream().map(b -> b.getBenefitItemNo()).collect(Collectors.toList()));
                benefitResponseDTO.setBenefitItemResponseDTOS(benefitItemResponseDTOMapper.toDtoList(benefitItems));

                //set licenses
                benefitLicenses = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
                licenses = benefitLicenses.stream().map(b -> b.getLicense()).collect(Collectors.toList());
                benefitResponseDTO.setLicenseResponseDTOS(licenseResponseDTOMapper.toDtoList(licenses));

                benefitResponseDTOS.add(benefitResponseDTO);
            }
        }
        return benefitResponseDTOS;
    }

    @Override
    public PagingResponseModel<BenefitResponseDTO> search(String searchValue, Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Benefit> benefitPage = benefitRepository.findAllBySearchValue(searchValue, pageable);

        if (!benefitPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Benefit> benefits = benefitPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", benefits.size(), searchValue);

        List<BenefitResponseDTO> benefitResponseDTOS = benefitResponseDTOMapper.toDtoList(benefits);

        List<BenefitLicense> benefitLicenses;
        List<License> licenses;
        List<BenefitItem> benefitItems;
        Long benefitNo;
        for (BenefitResponseDTO benefitResponseDTO : benefitResponseDTOS) {
            benefitNo = benefitResponseDTO.getBenefitNo();

            //set benefit items
            benefitItems = benefitItemRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
            benefitResponseDTO.setBenefitItemNos(benefitItems.stream().map(b -> b.getBenefitItemNo()).collect(Collectors.toList()));
            benefitResponseDTO.setBenefitItemResponseDTOS(benefitItemResponseDTOMapper.toDtoList(benefitItems));

            //set licenses
            benefitLicenses = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
            licenses = benefitLicenses.stream().map(b -> b.getLicense()).collect(Collectors.toList());
            benefitResponseDTO.setLicenseResponseDTOS(licenseResponseDTOMapper.toDtoList(licenses));
        }

        return new PagingResponseModel<>(new PageImpl<>(benefitResponseDTOS,
                pageable,
                benefitPage.getTotalElements()));
    }

    @Override
    @Transactional
    public BenefitResponseDTO create(BenefitRequestDTO benefitRequestDTO) throws HIASException {
        String benefitCode = benefitRequestDTO.getBenefitCode();
        log.info("[create] Start create new benefit.");
        if (benefitValidator.isBenefitCodeExistance(benefitCode)) {
            throw HIASException.buildHIASException(FieldNameConstant.BENEFIT_CODE,
                    messageUtils.getMessage(ErrorMessageCode.BENEFIT_CODE_EXISTENCE)
                    , HttpStatus.NOT_ACCEPTABLE);
        }
        BenefitResponseDTO benefitResponseDTO;
        Benefit benefitCreated = benefitRepository.save(benefitRequestDTOMapper.toEntity(benefitRequestDTO));
        List<Long> licenseNos = benefitRequestDTO.getLicenseNos();
        if (CollectionUtils.isNotEmpty(licenseNos)) {
            List<BenefitLicense> benefitLicenses = new ArrayList<>();
            for (Long licenseNo : licenseNos) {
                BenefitLicense benefitLicense = BenefitLicense.builder()
                        .benefitNo(benefitCreated.getBenefitNo())
                        .benefit(benefitCreated)
                        .licenseNo(licenseNo)
                        .license(License.builder().licenseNo(licenseNo).build())
                        .build();
                benefitLicenses.add(benefitLicense);
            }
            benefitLiscenseRepository.saveAll(benefitLicenses);
        }

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
            List<PolicyCoverage> policyCoverageList = policyCoverageRepository.findAllByBenefitNoAndIsDeletedIsFalse(benefitNo);
            if (!policyCoverageList.isEmpty()) {
                for (PolicyCoverage policyCoverage : policyCoverageList) {
                    policyCoverage.setDeleted(true);
                }
                policyCoverageRepository.saveAll(policyCoverageList);
            }
            List<BenefitLicense> benefitLicenseList = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
            if (!benefitLicenseList.isEmpty()) {
                for (BenefitLicense benefitLicense : benefitLicenseList) {
                    benefitLicense.setDeleted(true);
                }
                benefitLiscenseRepository.saveAll(benefitLicenseList);
            }
            List<BenefitItem> benefitItemList = benefitItemRepository.findAllByBenefitNoAndIsDeletedIsFalse(benefitNo);
            if (!benefitItemList.isEmpty()) {
                for (BenefitItem benefitItem : benefitItemList) {
                    benefitItem.setDeleted(true);
                }
                benefitItemRepository.saveAll(benefitItemList);
            }
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
            processForBenefitLicense(benefitRequestDTO, benefitNo, benefit);
            log.info("[update] Updated benefit with benefitNo : {} in the system.", benefitNo);
        }
        return benefitResponseDTO;
    }

    private void processForBenefitLicense(BenefitRequestDTO benefitRequestDTO, Long benefitNo, Benefit benefit) {
        List<BenefitLicense> benefitLicenses = benefitLiscenseRepository.findByBenefitNo(benefitNo);
        Map<Long, BenefitLicense> benefitLicenseMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(benefitLicenses)) {
            benefitLicenseMap = benefitLicenses.stream()
                    .collect(Collectors.toMap(BenefitLicense::getLicenseNo, Function.identity()));
        }
        List<BenefitLicense> savedBenefitLicenses = new ArrayList<>();
        List<Long> licenseNos = benefitRequestDTO.getLicenseNos();
        
        for (Long licenseNo : licenseNos) {
            BenefitLicense benefitLicense = benefitLicenseMap.get(licenseNo);
            if (benefitLicense == null) {
                savedBenefitLicenses.add(BenefitLicense
                        .builder()
                        .benefitNo(benefitNo)
                        .benefit(benefit)
                        .licenseNo(licenseNo)
                        .license(License.builder().licenseNo(licenseNo).build())
                        .build());
            } else {
                if (benefitLicense.isDeleted()) {
                    benefitLicense.setDeleted(Boolean.FALSE);
                    savedBenefitLicenses.add(benefitLicense);
                }
            }
        }

        //remove old licenses
        savedBenefitLicenses.addAll(benefitLicenses.stream().filter(b -> !licenseNos.contains(b.getLicenseNo()) && !b.isDeleted())
                .peek(b -> b.setDeleted(Boolean.TRUE))
                .collect(Collectors.toList()));

        benefitLiscenseRepository.saveAll(savedBenefitLicenses);
    }
}
