package com.hias.repository;

import com.hias.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findByProvinceNoAndIsDeletedIsFalse(Long provinceNo);

}
