package com.hias.repository;

import com.hias.entity.HealthCardFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HealthCardFormatRepository extends JpaRepository<HealthCardFormat, Long> {

    List<HealthCardFormat> findByClientNoAndIsDeletedIsFalse(Long clientNo);
}
