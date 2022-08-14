package com.hias.repository;

import com.hias.entity.Employee;
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

    @Query("select e.employeeNo from Employee e " +
            "left join Claim c on e.employeeNo = c.businessAppraisalBy " +
            "and (c.isDeleted is null or c.isDeleted = false) and " +
            "e.isDeleted = false " +
            "where c.businessExaminationDate is null and " +
            "(c.statusCode is null or c.statusCode <> :#{T(com.hias.constant.StatusCode).BUSINESS_APPROVED}) and " +
            "e.employmentType.employmentTypeCode = :#{T(com.hias.constant.EmploymentTypeConstant).BA} " +
            "group by e.employeeNo " +
            "order by count(e) asc,e.modifiedOn desc")
    Optional<Long> findBusinessAppraiserHasClaimAtLeast();

    @Query("select e.employeeNo from Employee e " +
            "join Claim c on e.employeeNo = c.medicalAppraisalBy and c.isDeleted = false and e.isDeleted = false " +
            "where c.medicalExaminationDate is null and " +
            "c.statusCode <> :#{T(com.hias.constant.StatusCode).MEDICAL_APPROVED} and " +
            "e.employmentType.employmentTypeCode = :#{T(com.hias.constant.EmploymentTypeConstant).MA} " +
            "group by e.employeeNo " +
            "order by count(e.employeeNo) asc,e.modifiedOn desc")
    Optional<Long> findMedicalAppraiserHasClaimAtLeast();
}
