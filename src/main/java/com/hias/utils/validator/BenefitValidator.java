package com.hias.utils.validator;

import com.hias.entity.Benefit;
import com.hias.repository.BenefitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class BenefitValidator {

    private final BenefitRepository benefitRepository;

    public boolean isBenefitCodeExistance(String benefitCode) {
        List<Benefit> benefits = benefitRepository.findByBenefitCodeAndIsDeletedIsFalse(benefitCode);
        if (CollectionUtils.isNotEmpty(benefits)) {
            log.error("[isBenefitCodeExistance] benefit code : {} has already exist.", benefitCode);
            return true;
        }
        return false;
    }
}
