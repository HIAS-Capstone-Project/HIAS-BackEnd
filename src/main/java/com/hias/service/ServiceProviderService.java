package com.hias.service;

import com.hias.entity.ServiceProvider;
import com.hias.model.request.ServiceProviderRequestDTO;
import com.hias.model.response.ServiceProviderResponseDTO;

import java.util.List;

public interface ServiceProviderService {
    List<ServiceProviderResponseDTO> findServiceProvider(String key, Integer pageIndex, Integer pageSize, String[] sort);

    void deleteServiceProviderByID(Long serviceProviderNo) throws Exception;

    ServiceProvider saveServiceProvider(ServiceProviderRequestDTO member);

    ServiceProviderResponseDTO findServiceProviderByID(Long serviceProviderNo);
}
