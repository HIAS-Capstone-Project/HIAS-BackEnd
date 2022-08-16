package com.hias.repository;


import com.hias.entity.PolicyCoverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyCoverageRepository extends JpaRepository<PolicyCoverage, Long> {
    List<PolicyCoverage> findAllByPolicyNo(Long policyNo);

    List<PolicyCoverage> findAllByBenefitNo(Long benefitNo);

    List<PolicyCoverage> findAllByPolicyNoAndIsDeletedIsFalse(Long policyNo);

    List<PolicyCoverage> findAllByBenefitNoAndIsDeletedIsFalse(Long benefitNo);

    @Query("select pc from PolicyCoverage pc where pc.isDeleted = false and pc.policyNo in :policyNos")
    List<PolicyCoverage> findAllByPolicyNos(List<Long> policyNos);
}
