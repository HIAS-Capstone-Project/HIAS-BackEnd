package com.hias.repository;

import com.hias.entity.Benefit;
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


    List<Client> findByCorporateIDAndIsDeletedIsFalse(String corporateID);

    List<Client> findByIsDeletedIsFalse();

    Optional<Client> findByClientNoAndIsDeletedIsFalse(Long clientNo);

    @Query("select c from Client c " +
            "where c.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(c.corporateID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(c.name) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Client> findAllBySearchValue(String searchValue, Pageable pageable);
}
