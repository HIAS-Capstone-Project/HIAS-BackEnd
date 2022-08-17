package com.hias.repository;

import com.hias.entity.ClientBusinessSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientBusinessSectorRepository extends JpaRepository<ClientBusinessSector, Long> {
    List<ClientBusinessSector> findAllByClientNoAndIsDeletedIsFalse(Long clientNo);

    List<ClientBusinessSector> findAllByBusinessSectorNoAndIsDeletedIsFalse(Long businessSectorNo);

    List<ClientBusinessSector> findAllByClientNo(Long clientNo);
}
