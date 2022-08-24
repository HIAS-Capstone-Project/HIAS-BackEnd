package com.hias.repository;

import com.hias.entity.Benefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    Optional<Benefit> findByBenefitNoAndIsDeletedIsFalse(Long benefitNo);

    List<Benefit> findAllByIsDeletedIsFalse();

    List<Benefit> findByBenefitCodeAndIsDeletedIsFalse(String benefitCode);

    @Query("select b from Benefit b " +
            "where b.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(b.benefitCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(b.benefitName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Benefit> findAllBySearchValue(String searchValue, Pageable pageable);

    @Query("select b from Benefit b join PolicyCoverage pc on pc.benefitNo = b.benefitNo " +
            "where b.isDeleted = false " +
            "and pc.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(b.benefitCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(b.benefitName) like concat('%',lower(trim(:searchValue)),'%'))" +
            "and pc.policy.clientNo = :clientNo")
    Page<Benefit> findAllBySearchValueForClient(Long clientNo, String searchValue, Pageable pageable);

    @Query("select b from Benefit b " +
            "join PolicyCoverage pc on pc.benefitNo = b.benefitNo " +
            "join Member m on m.policyNo = pc.policyNo " +
            "where b.isDeleted = false " +
            "and pc.isDeleted = false " +
            "and m.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(b.benefitCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(b.benefitName) like concat('%',lower(trim(:searchValue)),'%'))" +
            "and m.memberNo = :memberNo")
    Page<Benefit> findAllBySearchValueForMember(Long memberNo, String searchValue, Pageable pageable);
}
