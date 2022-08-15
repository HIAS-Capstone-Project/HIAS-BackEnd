package com.hias.service.impl;

import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.entity.Benefit;
import com.hias.entity.BenefitItem;
import com.hias.entity.Member;
import com.hias.entity.PolicyCoverage;
import com.hias.exception.HIASException;
import com.hias.mapper.request.BenefitRequestDTOMapper;
import com.hias.mapper.response.BenefitItemResponseDTOMapper;
import com.hias.mapper.response.BenefitResponseDTOMapper;
import com.hias.model.request.BenefitRequestDTO;
import com.hias.model.response.BenefitItemResponseDTO;
import com.hias.model.response.BenefitResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.BenefitItemRepository;
import com.hias.repository.BenefitRepository;
import com.hias.repository.MemberRepository;
import com.hias.repository.PolicyCoverageRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;
    private final MemberRepository memberRepository;
    private final BenefitRequestDTOMapper benefitRequestDTOMapper;
    private final BenefitResponseDTOMapper benefitResponseDTOMapper;
    private final BenefitItemResponseDTOMapper benefitItemResponseDTOMapper;
    private final BenefitValidator benefitValidator;
    private final MessageUtils messageUtils;
    private final BenefitItemRepository benefitItemRepository;

    private final PolicyCoverageRepository policyCoverageRepository;

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

            for (Benefit benefit : benefits) {
                BenefitResponseDTO benefitResponseDTO = benefitResponseDTOMapper.toDto(benefit);
                List<BenefitItem> benefitItems = benefitItemRepository.findByBenefitNoAndIsDeletedIsFalse(benefit.getBenefitNo());
                List<BenefitItemResponseDTO> benefitItemResponseDTOS = benefitItemResponseDTOMapper.toDtoList(benefitItems);
                benefitResponseDTO.setBenefitItemResponseDTOS(benefitItemResponseDTOS);
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

        benefitResponseDTOS.forEach(b -> b.setBenefitItemNos(benefitItemRepository.findByBenefitNoAndIsDeletedIsFalse(b.getBenefitNo()).
                stream().map(BenefitItem::getBenefitItemNo).collect(Collectors.toList())));

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
