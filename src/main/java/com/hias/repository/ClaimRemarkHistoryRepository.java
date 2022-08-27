package com.hias.repository;

import com.hias.entity.Bank;
import com.hias.entity.ClaimRemarkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRemarkHistoryRepository extends JpaRepository<ClaimRemarkHistory, Long> {

    List<Bank> findAllByIsDeletedIsFalse();

}
