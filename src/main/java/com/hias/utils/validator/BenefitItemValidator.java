package com.hias.utils.validator;

import com.hias.entity.BenefitItem;
import com.hias.repository.BenefitItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class BenefitItemValidator {

    private final BenefitItemRepository benefitItemRepository;

    public boolean isBenefitItemCodeExistance(String benefitCode, Long benefitNo) {
        List<BenefitItem> benefitItems = benefitItemRepository.findByBenefitItemCodeAndBenefitNoAndIsDeletedIsFalse(benefitCode, benefitNo);
        if (CollectionUtils.isNotEmpty(benefitItems)) {
            log.error("[isBenefitItemCodeExistance] benefitItemCode : {} , benefitNo : {} has already exist.",
                    benefitCode, benefitNo);
            return true;
        }
        return false;
    }
}
