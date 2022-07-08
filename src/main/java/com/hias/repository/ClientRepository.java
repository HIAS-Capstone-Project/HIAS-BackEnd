package com.hias.repository;

import com.hias.entity.Client;
import com.hias.model.response.ClientResponeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByIsDeletedIsFalse();
    Client findByClientNoAndIsDeletedIsFalse(Long clientNo);
}
