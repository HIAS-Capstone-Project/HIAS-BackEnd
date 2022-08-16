package com.hias.repository;

import com.hias.entity.BenefitLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitLiscenseRepository extends JpaRepository<BenefitLicense, Long> {

    List<BenefitLicense> findAllByIsDeletedIsFalse();

    List<BenefitLicense> findByBenefitNoAndIsDeletedIsFalse(Long benefitNo);

    List<BenefitLicense> findByBenefitNo(Long benefitNo);

}
