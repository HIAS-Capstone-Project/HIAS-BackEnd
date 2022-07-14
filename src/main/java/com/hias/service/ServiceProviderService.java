package com.hias.service;

import com.hias.entity.ServiceProvider;
import com.hias.exception.HIASException;
import com.hias.model.request.ServiceProviderRequestDTO;
import com.hias.model.response.PagingResponse;
import com.hias.model.response.ServiceProviderResponseDTO;


public interface ServiceProviderService {
    PagingResponse findServiceProvider(String key, Integer pageIndex, Integer pageSize, String[] sort);

    void deleteServiceProviderByID(Long serviceProviderNo) throws Exception;

    ServiceProvider saveServiceProvider(ServiceProviderRequestDTO member) throws HIASException;

    ServiceProviderResponseDTO findServiceProviderByID(Long serviceProviderNo);
}
