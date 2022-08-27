package com.hias.repository;

import com.hias.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    List<Client> findByCorporateIDIgnoreCaseAndIsDeletedIsFalse(String corporateID);

    List<Client> findByIsDeletedIsFalse();

    @Query("select c from Client c join EmployeeClient ec on c.clientNo = ec.clientNo " +
            "where c.isDeleted = false and ec.isDeleted = false " +
            "and ec.employeeNo = :employeeNo")
    List<Client> findByEmployeeNo(Long employeeNo);

    Optional<Client> findByClientNoAndIsDeletedIsFalse(Long clientNo);

    @Query("select c from Client c " +
            "where c.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.corporateID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.clientName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Client> findAllBySearchValue(String searchValue, Pageable pageable);

    @Query("select c from Client c join EmployeeClient ec on c.clientNo = ec.clientNo " +
            "where c.isDeleted = false and ec.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.corporateID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.clientName) like concat('%',lower(trim(:searchValue)),'%'))" +
            "and ec.employeeNo = :employeeNo")
    Page<Client> findAllBySearchValueForEmployee(Long employeeNo, String searchValue, Pageable pageable);
}
