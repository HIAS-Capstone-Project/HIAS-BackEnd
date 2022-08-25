package com.hias.repository;

import com.hias.constant.StatusCode;
import com.hias.entity.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNoAndIsDeletedIsFalse(Long claimNo);

    List<Claim> findAllByIsDeletedIsFalse();

    @Query("select c from Claim c " +
            "where c.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.claimID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.medicalAddress) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.description) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.remark) like concat('%',lower(trim(:searchValue)),'%')) " +
            "and (:clientNo is null or c.member.clientNo = :clientNo) " +
            "and (:statusCode is null or c.statusCode = :statusCode)")
    Page<Claim> findAllBySearchValue(String searchValue, Long clientNo, StatusCode statusCode, Pageable pageable);

    @Query("select c from Claim c " +
            "where c.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.claimID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.medicalAddress) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.description) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.remark) like concat('%',lower(trim(:searchValue)),'%')) " +
            "and (:clientNo is null or c.member.clientNo = :clientNo) " +
            "and (:statusCode is null or c.statusCode = :statusCode) " +
            "and c.memberNo = :memberNo")
    Page<Claim> findAllBySearchValueForMember(Long memberNo, String searchValue, Long clientNo, StatusCode statusCode, Pageable pageable);

    @Query("select c from Claim c " +
            "where c.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.claimID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.medicalAddress) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.description) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.remark) like concat('%',lower(trim(:searchValue)),'%')) " +
            "and (:clientNo is null or c.member.clientNo = :clientNo) " +
            "and (:statusCode is null or c.statusCode = :statusCode) " +
            "and c.serviceProviderNo = :serviceProviderNo")
    Page<Claim> findAllBySearchValueForServiceProvider(Long serviceProviderNo, String searchValue, Long clientNo, StatusCode statusCode, Pageable pageable);

    @Query("select c from Claim c " +
            "where c.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.claimID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.medicalAddress) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.description) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.remark) like concat('%',lower(trim(:searchValue)),'%')) " +
            "and (:clientNo is null or c.member.clientNo = :clientNo) " +
            "and (:statusCode is null or c.statusCode = :statusCode) " +
            "and  (c.businessAppraisalBy = :employeeNo " +
            "or c.medicalAppraisalBy = :employeeNo " +
            "or c.approvedBy = :employeeNo " +
            "or c.paidBy = :employeeNo)")
    Page<Claim> findAllBySearchValueForEmployee(Long employeeNo, String searchValue, Long clientNo, StatusCode statusCode, Pageable pageable);
}
