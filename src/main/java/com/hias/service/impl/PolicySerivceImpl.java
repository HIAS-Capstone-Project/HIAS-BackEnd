package com.hias.service.impl;

import com.hias.entity.Policy;
import com.hias.mapper.PolicyRequestDTOMapper;
import com.hias.mapper.PolicyResponseDTOMapper;
import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PolicyResponseDTO;
import com.hias.repository.PolicyRepository;
import com.hias.service.PolicyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PolicySerivceImpl implements PolicyService {
    private final PolicyRepository policyRepository;
    private final PolicyResponseDTOMapper policyResponseDTOMapper;
    private final PolicyRequestDTOMapper policyRequestDTOMapper;

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
        Policy policy = policyRepository.findByPolicyNoAndIsDeletedIsFalse(policyNo);
        PolicyResponseDTO policyResponseDTO = new PolicyResponseDTO();
        if (policy != null) {
            policyResponseDTO = policyResponseDTOMapper.toDto(policy);
        }
        return policyResponseDTO;
    }

    @Override
    public PolicyResponseDTO createPolicy(PolicyRequestDTO policyRequestDTO) {
        log.info("[createPolicy] get create policy ");
        Policy policy = policyRepository.save(policyRequestDTOMapper.toEntity(policyRequestDTO));
        return policyResponseDTOMapper.toDto(policy);
    }

    @Override
    public PolicyResponseDTO updatePolicy(PolicyRequestDTO policyRequestDTO) {
        Policy policy = policyRepository.findByPolicyNoAndIsDeletedIsFalse(policyRequestDTO.getPolicyNo());
        PolicyResponseDTO policyResponseDTO = new PolicyResponseDTO();
        if (policy != null) {
            policy.setPolicyCode(policyRequestDTO.getPolicyCode());
            policy.setPolicyName(policyRequestDTO.getPolicyName());
            policy.setClientNo(policyRequestDTO.getClientNo());
            policy.setRemark(policyRequestDTO.getRemark());
            policyResponseDTO = policyResponseDTOMapper.toDto(policyRepository.save(policy));
        }
        return policyResponseDTO;
    }

    @Override
    public PolicyResponseDTO deletePolicy(Long policyNo) {
        Policy policy = policyRepository.findByPolicyNoAndIsDeletedIsFalse(policyNo);
        PolicyResponseDTO policyResponseDTO = new PolicyResponseDTO();
        if (policy != null) {
            policy.setDeleted(true);
            policyResponseDTO = policyResponseDTOMapper.toDto(policyRepository.save(policy));
        }
        return policyResponseDTO;
    }
}
