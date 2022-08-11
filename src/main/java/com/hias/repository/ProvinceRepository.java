package com.hias.repository;

import com.hias.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    List<Province> findAllByIsDeletedIsFalse();

    Optional<Province> findProvinceByProvinceNoAndIsDeletedIsFalse(Long provinceNo);
}
