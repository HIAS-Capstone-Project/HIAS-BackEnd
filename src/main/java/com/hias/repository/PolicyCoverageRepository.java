package com.hias.repository;


import com.hias.entity.PolicyCoverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyCoverageRepository extends JpaRepository<PolicyCoverage, Long> {
}
