package com.hias.service.impl;

import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.constant.RoleEnum;
import com.hias.entity.BenefitItem;
import com.hias.exception.HIASException;
import com.hias.mapper.request.BenefitItemRequestDTOMapper;
import com.hias.mapper.response.BenefitItemResponseDTOMapper;
import com.hias.model.request.BenefitItemRequestDTO;
import com.hias.model.response.BenefitItemResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.BenefitItemRepository;
import com.hias.security.dto.UserDetail;
import com.hias.service.BenefitItemService;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.BenefitItemValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BenefitItemServiceImpl implements BenefitItemService {

    private final BenefitItemRepository benefitItemRepository;
    private final BenefitItemRequestDTOMapper benefitItemRequestDTOMapper;
    private final BenefitItemResponseDTOMapper benefitItemResponseDTOMapper;
    private final BenefitItemValidator benefitItemValidator;
    private final MessageUtils messageUtils;

    @Override
    public BenefitItemResponseDTO findByBenefitItemNo(Long benefitItemNo) {
        BenefitItemResponseDTO benefitItemResponseDTO = new BenefitItemResponseDTO();
        Optional<BenefitItem> benefitItemOptional = benefitItemRepository.findByBenefitItemNoAndIsDeletedIsFalse(benefitItemNo);
        if (!benefitItemOptional.isPresent()) {
            log.warn("[findByBenefitItemNo] Cannot found benefit with benefit item no : {}", benefitItemNo);
            return benefitItemResponseDTO;
        }
        log.info("[findByBenefitItemNo] Found benefit with benefit item no : {}", benefitItemNo);
        return benefitItemResponseDTOMapper.toDto(benefitItemOptional.get());
    }

    @Override
    public List<BenefitItemResponseDTO> findAll() {
        List<BenefitItemResponseDTO> benefitItemResponseDTOS = new ArrayList<>();
        List<BenefitItem> benefitItems = benefitItemRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(benefitItems)) {
            benefitItemResponseDTOS = benefitItemResponseDTOMapper.toDtoList(benefitItems);
        }
        return benefitItemResponseDTOS;
    }

    @Override
    public PagingResponseModel<BenefitItemResponseDTO> search(String searchValue, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue,
                pageNumber,
                pageSize);

        Page<BenefitItem> benefitItemPage = this.buildBenefitItemPageByRole(searchValue, pageable);

        if (!benefitItemPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<BenefitItem> benefits = benefitItemPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", benefits.size(), searchValue);

        List<BenefitItemResponseDTO> benefitItemResponseDTOS = benefitItemResponseDTOMapper.toDtoList(benefits);

        return new PagingResponseModel<>(new PageImpl<>(benefitItemResponseDTOS,
                pageable,
                benefitItemPage.getTotalElements()));
    }

    private Page<BenefitItem> buildBenefitItemPageByRole(String searchValue, Pageable pageable) {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RoleEnum roleEnum = null;
        Long primaryKey = null;
        if (userDetail != null) {
            roleEnum = RoleEnum.findByString(userDetail.getRoles().get(0));
            primaryKey = userDetail.getPrimaryKey();
        }
        Page<BenefitItem> benefitItemPage = Page.empty();
        if (roleEnum == null || Arrays.asList(RoleEnum.ROLE_CLIENT, RoleEnum.ROLE_SERVICE_PROVIDER,
                        RoleEnum.ROLE_HEALTH_MODERATOR, RoleEnum.ROLE_MEDICAL_APPRAISER,
                        RoleEnum.ROLE_BUSINESS_APPRAISER, RoleEnum.ROLE_ACCOUNTANT)
                .contains(roleEnum)) {
            benefitItemPage = benefitItemRepository.findAllBySearchValue(searchValue, pageable);
        }
        if (RoleEnum.ROLE_CLIENT.equals(roleEnum)) {
            benefitItemPage = benefitItemRepository.findAllBySearchValueForClient(primaryKey, searchValue, pageable);
        }
        if (RoleEnum.ROLE_MEMBER.equals(roleEnum)) {
            benefitItemPage = benefitItemRepository.findAllBySearchValueForMember(primaryKey, searchValue, pageable);
        }
        return benefitItemPage;
    }

    @Override
    @Transactional
    public BenefitItemResponseDTO create(BenefitItemRequestDTO benefitItemRequestDTO) throws HIASException {
        String benefitCode = benefitItemRequestDTO.getBenefitItemCode();
        Long benefitNo = benefitItemRequestDTO.getBenefitNo();
        log.info("[create] Start create new benefit item.");
        if (benefitItemValidator.isBenefitItemCodeExistance(benefitCode, benefitNo)) {
            throw HIASException.buildHIASException(FieldNameConstant.BENEFIT_ITEM_CODE,
                    messageUtils.getMessage(ErrorMessageCode.BENEFIT_ITEM_CODE_EXISTENCE)
                    , HttpStatus.NOT_ACCEPTABLE);
        }
        BenefitItemResponseDTO benefitItemResponseDTO;
        BenefitItem benefitItem = benefitItemRepository.save(benefitItemRequestDTOMapper.toEntity(benefitItemRequestDTO));
        benefitItemResponseDTO = benefitItemResponseDTOMapper.toDto(benefitItem);
        log.info("[create] End create new benefit with benefit item no : {}", benefitItemResponseDTO.getBenefitNo());
        return benefitItemResponseDTO;
    }

    @Override
    @Transactional
    public BenefitItemResponseDTO update(BenefitItemRequestDTO benefitItemRequestDTO) {
        BenefitItemResponseDTO benefitItemResponseDTO = new BenefitItemResponseDTO();
        Long benefitItemNo = benefitItemRequestDTO.getBenefitItemNo();
        Optional<BenefitItem> benefitItemOptional = benefitItemRepository.findByBenefitItemNoAndIsDeletedIsFalse(benefitItemNo);
        if (!benefitItemOptional.isPresent()) {
            log.info("[update] Cannot found benefit item with benefitItemNo : {} in the system.", benefitItemNo);
        } else {
            BenefitItem benefitItem = benefitItemRequestDTOMapper.toEntity(benefitItemRequestDTO);
            benefitItemResponseDTO = benefitItemResponseDTOMapper.toDto(benefitItemRepository.save(benefitItem));
            log.info("[update] Updated benefit with benefitItemNo : {} in the system.", benefitItemNo);
        }
        return benefitItemResponseDTO;
    }

    @Override
    @Transactional
    public BenefitItemResponseDTO deleteByBenefitItemNo(Long benefitItemNo) {
        BenefitItemResponseDTO benefitItemResponseDTO = new BenefitItemResponseDTO();
        Optional<BenefitItem> benefitOptional = benefitItemRepository.findByBenefitItemNoAndIsDeletedIsFalse(benefitItemNo);
        if (!benefitOptional.isPresent()) {
            log.info("[deleteByBenefitItemNo] Cannot found benefit item with benefitItemNo : {} in the system.", benefitItemNo);
        } else {
            BenefitItem benefitItem = benefitOptional.get();
            benefitItem.setDeleted(Boolean.TRUE);
            benefitItemResponseDTO = benefitItemResponseDTOMapper.toDto(benefitItemRepository.save(benefitItem));
            log.info("[deleteByBenefitItemNo] Delete benefit item with benefitItemNo : {} in the system.", benefitItemNo);
        }
        return benefitItemResponseDTO;
    }
}
