package com.hias.repository;

import com.hias.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findByPolicyCodeAndClientNoAndIsDeletedIsFalse(String policyCode, Long clientNo);

    List<Policy> findByIsDeletedIsFalse();

    List<Policy> findAllByClientNoAndIsDeletedIsFalse(Long clientNo);

    Optional<Policy> findByPolicyNoAndIsDeletedIsFalse(Long policyNo);

    @Query("select p from Policy p " +
            "where p.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(p.policyCode) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(p.policyName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Policy> findAllBySearchValue(String searchValue, Pageable pageable);
}

