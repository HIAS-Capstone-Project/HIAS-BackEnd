package com.hias.repository;

import com.hias.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    Optional<Benefit> findByBenefitNoAndIsDeletedIsFalse(Long benefitNo);

    List<Benefit> findAllByIsDeletedIsFalse();

    List<Benefit> findByBenefitCodeAndIsDeletedIsFalse(String benefitCode);
}
