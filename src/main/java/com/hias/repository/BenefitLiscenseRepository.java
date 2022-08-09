package com.hias.repository;

import com.hias.entity.BenefitLiscense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitLiscenseRepository extends JpaRepository<BenefitLiscense, Long> {

    List<BenefitLiscense> findAllByIsDeletedIsFalse();

    List<BenefitLiscense> findByBenefitNoAndIsDeletedIsFalse(Long benefitNo);

}
