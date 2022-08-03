package com.hias.repository;

import com.hias.entity.Employee;
import com.hias.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false AND (e.employeeName LIKE %?1% OR e.department.departmentName LIKE %?1%)")
    Page<Employee> findEmployee(String keyOne, Pageable pageable);

    List<Employee> findByEmployeeIDAndIsDeletedIsFalse(String employeeID);

    Optional<Employee> findByEmployeeNoAndIsDeletedIsFalse(Long employeeNo);

    @Query("select e from Employee e " +
            "where e.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(e.employeeID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(e.employeeName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Employee> findAllBySearchValue(String searchValue, Pageable pageable);
}
