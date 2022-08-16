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

    List<BenefitItem> findByBenefitItemCodeAndBenefitNoAndIsDeletedIsFalse(String benefitItemCode, Long benefitNo);

    @Query("select b from BenefitItem b " +
            "where b.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(b.benefitItemCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(b.benefitItemName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<BenefitItem> findAllBySearchValue(String searchValue, Pageable pageable);

    List<BenefitItem> findByBenefitNoAndIsDeletedIsFalse(Long benefitNo);
}
