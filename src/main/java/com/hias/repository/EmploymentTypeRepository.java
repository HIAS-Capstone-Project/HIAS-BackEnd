package com.hias.repository;


import com.hias.entity.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, Long> {

    Optional<EmploymentType> findByEmploymentTypeNo(Long departmentNo);
}
