package com.hias.repository;

import com.hias.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByIsDeletedIsFalse();

    Policy findByPolicyNoAndIsDeletedIsFalse(Long policyNo);

}

