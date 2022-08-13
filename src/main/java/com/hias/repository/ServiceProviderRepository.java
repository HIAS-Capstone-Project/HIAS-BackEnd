package com.hias.repository;

import com.hias.entity.ServiceProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    @Query("SELECT s FROM ServiceProvider s WHERE s.isDeleted = false AND (s.serviceProviderID LIKE %?1% OR s.serviceProviderName LIKE %?1% OR s.address LIKE %?1%)")
    Page<ServiceProvider> findServiceProvider(String keyOne, Pageable pageable);

    Optional<ServiceProvider> findByServiceProviderNoAndIsDeletedIsFalse(Long serviceProviderNo);
}
