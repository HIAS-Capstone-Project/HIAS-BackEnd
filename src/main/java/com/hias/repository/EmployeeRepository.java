package com.hias.repository;

import com.hias.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false AND (e.employeeName LIKE %?1% OR e.department.departmentName LIKE %?1%)")
    Page<Employee> findEmployee(String keyOne, Pageable pageable);

    List<Employee> findByEmployeeIDIgnoreCaseAndIsDeletedIsFalse(String employeeID);

    Optional<Employee> findByEmployeeNoAndIsDeletedIsFalse(Long employeeNo);

    @Query("select e from Employee e " +
            "where e.isDeleted = false " +
            "and e.employeeNo in :employeeNos")
    List<Employee> findByEmployeeNoIn(Set<Long> employeeNos);

    @Query("select e from Employee e " +
            "where e.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(e.employeeID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(e.employeeName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Employee> findAllBySearchValue(String searchValue, Pageable pageable);

    @Query("select e.employeeNo from Employee e " +
            "left join Claim c on e.employeeNo = c.businessAppraisalBy and c.businessAppraisalDate is null " +
            "and (c.statusCode is null or c.statusCode <> :#{T(com.hias.constant.StatusCode).BUSINESS_VERIFIED})" +
            "and (c.isDeleted is null or c.isDeleted = false) and " +
            "e.isDeleted = false " +
            "where e.employmentType.employmentTypeCode = :#{T(com.hias.constant.EmploymentTypeConstant).BA} " +
            "group by e.employeeNo " +
            "order by count(e) asc,e.modifiedOn desc")
    List<Long> findBusinessAppraiserHasClaimAtLeast();

    @Query("select e.employeeNo from Employee e " +
            "left join Claim c on e.employeeNo = c.medicalAppraisalBy " +
            "and (c.isDeleted is null or c.isDeleted = false) and e.isDeleted = false " +
            "and c.medicalAppraisalDate is null " +
            "and (c.statusCode is null or c.statusCode <> :#{T(com.hias.constant.StatusCode).MEDICAL_VERIFIED}) " +
            "where e.employmentType.employmentTypeCode = :#{T(com.hias.constant.EmploymentTypeConstant).MA} " +
            "group by e.employeeNo " +
            "order by count(e.employeeNo) asc,e.modifiedOn desc")
    List<Long> findMedicalAppraiserHasClaimAtLeast();

    @Query("select e.employeeNo from Employee e " +
            "left join Claim c on e.employeeNo = c.approvedBy " +
            "and (c.isDeleted is null or c.isDeleted = false) and e.isDeleted = false " +
            "and c.approvedDate is null " +
            "and (c.statusCode is null or c.statusCode <> :#{T(com.hias.constant.StatusCode).APPROVED}) " +
            "where e.employmentType.employmentTypeCode = :#{T(com.hias.constant.EmploymentTypeConstant).HM} " +
            "group by e.employeeNo " +
            "order by count(e.employeeNo) asc,e.modifiedOn desc")
    List<Long> findApproverHasClaimAtLeast();

    @Query("select e.employeeNo from Employee e " +
            "left join Claim c on e.employeeNo = c.paidBy and (c.isDeleted is null or c.isDeleted = false) " +
            "and e.isDeleted = false " +
            "and c.paymentDate is null " +
            "and (c.statusCode is null or c.statusCode <> :#{T(com.hias.constant.StatusCode).SETTLED}) " +
            "where e.employmentType.employmentTypeCode = :#{T(com.hias.constant.EmploymentTypeConstant).ACC} " +
            "group by e.employeeNo " +
            "order by count(e.employeeNo) asc,e.modifiedOn desc")
    List<Long> findAccountantHasClaimAtLeast();

    @Query("select e from Employee e where e.isDeleted = false " +
            "and e.employmentType.employmentTypeCode = :employmentTypeCode")
    List<Employee> findByEmploymentTypeCode(String employmentTypeCode);
}
