package com.hias.repository;

import com.hias.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false AND (e.employeeName LIKE %?1% OR e.department.departmentName LIKE %?1%)")
    Page<Employee> findEmployee(String keyOne, Pageable pageable);
}
