package com.hias.repository;

import com.hias.entity.BenefitItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitItemRepository extends JpaRepository<BenefitItem, Long> {

    List<BenefitItem> findAllByIsDeletedIsFalse();

    List<BenefitItem> findAllByBenefitNoAndIsDeletedIsFalse(Long benefitNo);

    Optional<BenefitItem> findByBenefitItemNoAndIsDeletedIsFalse(Long benefitItemNo);

    List<BenefitItem> findByBenefitItemCodeIgnoreCaseAndBenefitNoAndIsDeletedIsFalse(String benefitItemCode, Long benefitNo);

    @Query("select b from BenefitItem b " +
            "where b.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(b.benefitItemCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(b.benefitItemName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<BenefitItem> findAllBySearchValue(String searchValue, Pageable pageable);

    @Query("select b from BenefitItem b join PolicyCoverage pc on pc.benefitNo = b.benefitNo " +
            "where b.isDeleted = false " +
            "and pc.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(b.benefitItemCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(b.benefitItemName) like concat('%',lower(trim(:searchValue)),'%')) " +
            "and pc.policy.clientNo = :clientNo")
    Page<BenefitItem> findAllBySearchValueForClient(Long clientNo, String searchValue, Pageable pageable);

    @Query("select b from BenefitItem b " +
            "join PolicyCoverage pc on pc.benefitNo = b.benefitNo " +
            "join Member m on m.policyNo = pc.policyNo " +
            "where b.isDeleted = false " +
            "and pc.isDeleted = false " +
            "and m.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(b.benefitItemCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(b.benefitItemName) like concat('%',lower(trim(:searchValue)),'%')) " +
            "and m.memberNo = :memberNo")
    Page<BenefitItem> findAllBySearchValueForMember(Long memberNo, String searchValue, Pageable pageable);

    List<BenefitItem> findByBenefitNoAndIsDeletedIsFalse(Long benefitNo);
}
