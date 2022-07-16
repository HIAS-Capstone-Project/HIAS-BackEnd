package com.hias.repository;

import com.hias.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    List<Client> findByCorporateIDAndIsDeletedIsFalse(String corporateID);

    List<Client> findByIsDeletedIsFalse();

    Optional<Client> findByClientNoAndIsDeletedIsFalse(Long clientNo);
}
