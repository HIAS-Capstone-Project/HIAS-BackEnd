package com.hias.repository;

import com.hias.entity.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNoAndIsDeletedIsFalse(Long claimNo);

    List<Claim> findAllByIsDeletedIsFalse();

    @Query("select c from Claim c " +
            "where c.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.claimID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.medicalAddress) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.description) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.remark) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Claim> findAllBySearchValue(String searchValue, Pageable pageable);
}
