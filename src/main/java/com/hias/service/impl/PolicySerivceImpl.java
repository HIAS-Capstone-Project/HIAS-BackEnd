package com.hias.service.impl;

import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.constant.RoleEnum;
import com.hias.entity.Benefit;
import com.hias.entity.Member;
import com.hias.entity.Policy;
import com.hias.entity.PolicyCoverage;
import com.hias.exception.HIASException;
import com.hias.mapper.request.PolicyRequestDTOMapper;
import com.hias.mapper.response.PolicyResponseDTOMapper;
import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.model.response.PolicyResponseDTO;
import com.hias.repository.MemberRepository;
import com.hias.repository.PolicyCoverageRepository;
import com.hias.repository.PolicyRepository;
import com.hias.security.dto.UserDetail;
import com.hias.service.PolicyService;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.PolicyValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PolicySerivceImpl implements PolicyService {
    private final PolicyRepository policyRepository;
    private final PolicyResponseDTOMapper policyResponseDTOMapper;
    private final PolicyRequestDTOMapper policyRequestDTOMapper;
    private final PolicyValidator policyValidator;
    private final PolicyCoverageRepository policyCoverageRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<PolicyResponseDTO> getAll() {
        log.info("[getAll] get all policy");
        List<Policy> listPolicy = policyRepository.findByIsDeletedIsFalse();
        List<PolicyResponseDTO> policyResponseDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(listPolicy)) {
            policyResponseDTOS = policyResponseDTOMapper.toDtoList(listPolicy);
            log.info("[getAll] size of list policy {}", policyResponseDTOS.size());

        }
        return policyResponseDTOS;
    }

    @Override
    public PagingResponseModel<PolicyResponseDTO> search(String searchValue, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Policy> policyPage = this.buildPolicyPageByRole(searchValue, pageable);

        if (!policyPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Policy> policies = policyPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", policies.size(), searchValue);

        List<PolicyResponseDTO> policyResponseDTOS = policyResponseDTOMapper.toDtoList(policies);

        policyResponseDTOS.forEach(p -> p.setBenefitNos(policyCoverageRepository.findAllByPolicyNoAndIsDeletedIsFalse(p.getPolicyNo()).
                stream().map(PolicyCoverage::getBenefitNo).collect(Collectors.toList())));

        return new PagingResponseModel<>(new PageImpl<>(policyResponseDTOS,
                pageable,
                policyPage.getTotalElements()));
    }

    private Page<Policy> buildPolicyPageByRole(String searchValue, Pageable pageable) {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RoleEnum roleEnum = null;
        Long primaryKey = null;
        if (userDetail != null) {
            roleEnum = RoleEnum.findByString(userDetail.getRoles().get(0));
            primaryKey = userDetail.getPrimaryKey();
        }
        Page<Policy> policyPage = Page.empty();
        if (roleEnum == null || RoleEnum.ROLE_SYSTEM_ADMIN.equals(roleEnum)) {
            policyPage = policyRepository.findAllBySearchValue(searchValue, pageable);
        }
        if (RoleEnum.ROLE_CLIENT.equals(roleEnum)) {
            policyPage = policyRepository.findAllBySearchValueForClient(primaryKey, searchValue, pageable);
        }
        if (RoleEnum.ROLE_MEMBER.equals(roleEnum)) {
            policyPage = policyRepository.findAllBySearchValueForMember(primaryKey, searchValue, pageable);
        }
        return policyPage;
    }

    @Override
    public PolicyResponseDTO getDetail(Long policyNo) {
        log.info("[getDetail] get policy detail");
        Optional<Policy> policy = policyRepository.findByPolicyNoAndIsDeletedIsFalse(policyNo);
        PolicyResponseDTO policyResponseDTO = new PolicyResponseDTO();
        if (policy.isPresent()) {
            policyResponseDTO = policyResponseDTOMapper.toDto(policy.get());
        }
        return policyResponseDTO;
    }

    @Override
    @Transactional
    public PolicyResponseDTO create(PolicyRequestDTO policyRequestDTO) throws HIASException {
        log.info("[createPolicy] create policy ");
        String policyCode = policyRequestDTO.getPolicyCode();
        Long clientNo = policyRequestDTO.getClientNo();
        if (policyValidator.isPolicyCodeExistance(policyCode, clientNo)) {
            throw HIASException.buildHIASException(FieldNameConstant.POLICY_CODE,
                    MessageUtils.get().getMessage(ErrorMessageCode.POLICY_CODE_EXISTENCE),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        Policy policy = policyRepository.save(policyRequestDTOMapper.toEntity(policyRequestDTO));
        log.info("Created Policy with ID: {}", policy.getPolicyNo());
        List<PolicyCoverage> policyCoverages = new ArrayList<>();
        policyRequestDTO.getBenefitNos().forEach(o -> policyCoverages.add(PolicyCoverage.builder().policyNo(policy.getPolicyNo()).benefitNo(o).
                policy(Policy.builder().policyNo(policy.getPolicyNo()).build()).
                benefit(Benefit.builder().benefitNo(o).build()).build()));
        policyCoverageRepository.saveAll(policyCoverages);
        return policyResponseDTOMapper.toDto(policy);
    }

    @Override
    @Transactional
    public PolicyResponseDTO update(PolicyRequestDTO policyRequestDTO) {
        Optional<Policy> policy = policyRepository.findByPolicyNoAndIsDeletedIsFalse(policyRequestDTO.getPolicyNo());
        PolicyResponseDTO policyResponseDTO = new PolicyResponseDTO();
        if (policy.isPresent()) {
            Policy updatedPolicy = policyRequestDTOMapper.toEntity(policyRequestDTO);
            List<PolicyCoverage> policyCoverages = policyCoverageRepository.findAllByPolicyNo(updatedPolicy.getPolicyNo());
            List<PolicyCoverage> updatedPolicyCoverage = new ArrayList<>();
            policyRequestDTO.getBenefitNos().forEach(o -> {
                if (policyCoverages.stream().anyMatch(p -> Objects.equals(p.getBenefitNo(), o) && p.isDeleted())) {
                    updatedPolicyCoverage.add(policyCoverages.stream().filter(p -> (Objects.equals(p.getBenefitNo(), o))).
                            peek(p -> p.setDeleted(false)).findFirst().get());
                } else {
                    if (policyCoverages.stream().noneMatch(p -> Objects.equals(p.getBenefitNo(), o))) {
                        updatedPolicyCoverage.add(PolicyCoverage.builder().policyNo(updatedPolicy.getPolicyNo()).benefitNo(o).
                                policy(Policy.builder().policyNo(updatedPolicy.getPolicyNo()).build()).
                                benefit(Benefit.builder().benefitNo(o).build()).build());
                    }
                }
            });
            policyCoverages.forEach(o -> {
                if (policyRequestDTO.getBenefitNos().stream().noneMatch(b -> Objects.equals(b, o.getBenefitNo()))) {
                    o.setDeleted(true);
                    updatedPolicyCoverage.add(o);
                }
            });
            policyResponseDTO = policyResponseDTOMapper.toDto(policyRepository.save(updatedPolicy));
            log.info("Updated Policy");
            policyCoverageRepository.saveAll(updatedPolicyCoverage);
            log.info("Updated relevant benefits in Policy Coverage");
        }
        return policyResponseDTO;
    }

    @Override
    @Transactional
    public PolicyResponseDTO delete(Long policyNo) {
        Optional<Policy> optionalPolicy = policyRepository.findByPolicyNoAndIsDeletedIsFalse(policyNo);
        PolicyResponseDTO policyResponseDTO = new PolicyResponseDTO();
        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            List<Member> memberList = memberRepository.findMemberByPolicyNoAndIsDeletedIsFalse(policyNo);
            List<PolicyCoverage> policyCoverageList = policyCoverageRepository.findAllByPolicyNo(policyNo);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(memberList)) {
                for (PolicyCoverage policyCoverage : policyCoverageList) {
                    policyCoverage.setDeleted(true);
                }
                policyCoverageRepository.saveAll(policyCoverageList);
            }
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(memberList)) {
                for (Member member : memberList) {
                    member.setDeleted(true);
                }
                memberRepository.saveAll(memberList);
            }
            policy.setDeleted(true);
            policyResponseDTO = policyResponseDTOMapper.toDto(policyRepository.save(policy));
        }
        return policyResponseDTO;
    }
}
