package com.hias.repository;

import com.hias.entity.BusinessSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessSectorRepository extends JpaRepository<BusinessSector, Long> {
    Optional<BusinessSector> findByBusinessSectorNoAndIsDeletedIsFalse(Long businessSectorNo);

    List<BusinessSector> findAllByIsDeletedIsFalse();
}
