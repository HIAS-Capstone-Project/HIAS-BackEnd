package com.hias.utils.validator;

import com.hias.entity.Policy;
import com.hias.repository.PolicyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class PolicyValidator {

    private final PolicyRepository policyRepository;

    public boolean isPolicyCodeExistance(String policyCode, Long clientNo) {
        List<Policy> policies = policyRepository.findByPolicyCodeIgnoreCaseAndClientNoAndIsDeletedIsFalse(policyCode, clientNo);
        return CollectionUtils.isNotEmpty(policies);
    }


}
