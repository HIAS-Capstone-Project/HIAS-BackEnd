package com.hias.repository;

import com.hias.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNoAndIsDeletedIsFalse(Long claimNo);

    List<Claim> findAllByIsDeletedIsFalse();
}
