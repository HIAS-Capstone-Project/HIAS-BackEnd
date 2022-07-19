package com.hias.service.impl;

import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.entity.Policy;
import com.hias.exception.HIASException;
import com.hias.mapper.request.PolicyRequestDTOMapper;
import com.hias.mapper.response.PolicyResponseDTOMapper;
import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PolicyResponseDTO;
import com.hias.repository.PolicyRepository;
import com.hias.service.PolicyService;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.PolicyValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PolicySerivceImpl implements PolicyService {
    private final PolicyRepository policyRepository;
    private final PolicyResponseDTOMapper policyResponseDTOMapper;
    private final PolicyRequestDTOMapper policyRequestDTOMapper;
    private final PolicyValidator policyValidator;

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
        return policyResponseDTOMapper.toDto(policy);
    }

    @Override
    @Transactional
    public PolicyResponseDTO update(PolicyRequestDTO policyRequestDTO) {
        Optional<Policy> policy = policyRepository.findByPolicyNoAndIsDeletedIsFalse(policyRequestDTO.getPolicyNo());
        PolicyResponseDTO policyResponseDTO = new PolicyResponseDTO();
        if (policy.isPresent()) {
            Policy updatedPolicy = policyRequestDTOMapper.toEntity(policyRequestDTO);
            policyResponseDTO = policyResponseDTOMapper.toDto(policyRepository.save(updatedPolicy));
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
            policy.setDeleted(true);
            policyResponseDTO = policyResponseDTOMapper.toDto(policyRepository.save(policy));
        }
        return policyResponseDTO;
    }
}
