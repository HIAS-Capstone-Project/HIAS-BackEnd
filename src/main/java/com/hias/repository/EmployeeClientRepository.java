package com.hias.repository;


import com.hias.entity.EmployeeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeClientRepository extends JpaRepository<EmployeeClient, Long> {
    List<EmployeeClient> findByEmployeeNoAndIsDeletedIsFalse(Long employeeNo);

    List<EmployeeClient> findByClientNoAndIsDeletedIsFalse(Long clientNo);

    List<EmployeeClient> findByClientNo(Long clientNo);
}
