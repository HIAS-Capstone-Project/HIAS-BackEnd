package com.hias.service.impl;

import com.hias.entity.*;
import com.hias.mapper.ServiceProviderRequestDTOMapper;
import com.hias.mapper.ServiceProviderResponseDTOMapper;
import com.hias.model.request.ServiceProviderRequestDTO;
import com.hias.model.response.ServiceProviderResponseDTO;
import com.hias.repository.ServiceProviderRepository;
import com.hias.service.ServiceProviderService;
import com.hias.utilities.DirectionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final ServiceProviderResponseDTOMapper serviceProviderResponseDTOMapper;
    private final ServiceProviderRequestDTOMapper serviceProviderRequestDTOMapper;

    @Override
    public List<ServiceProviderResponseDTO> findServiceProvider(String key, Integer pageIndex, Integer pageSize, String[] sort) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 5 : pageSize;
        key = key == null ? "" : key;
        List<Sort.Order> orders = new ArrayList<>();
        String[] analyst;
        if (sort[0].contains(",")) {
            for (String s : sort) {
                analyst = s.split(",");
                orders.add(new Sort.Order(DirectionUtils.getDirection(analyst[1]), analyst[0]));
            }
        } else {
            orders.add(new Sort.Order(DirectionUtils.getDirection(sort[1]), sort[0]));
        }
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.by(orders));
        return serviceProviderResponseDTOMapper.toDtoList(serviceProviderRepository.findServiceProvider(key, pageable).toList());
    }

    @Override
    public void deleteServiceProviderByID(Long serviceProviderNo) throws Exception {
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(serviceProviderNo);
        if (serviceProvider.isPresent()) {
            ServiceProvider serviceProvider1 = serviceProvider.get();
            serviceProvider1.setDeleted(true);
            serviceProviderRepository.save(serviceProvider1);
        } else {
            throw new Exception("ServiceProvider not found");
        }
    }

    @Override
    public ServiceProvider saveServiceProvider(ServiceProviderRequestDTO serviceProviderRequestDTO) {
        ServiceProvider saveServiceProvider = serviceProviderRequestDTOMapper.toEntity(serviceProviderRequestDTO);
        if (serviceProviderRequestDTO.getServiceProviderNo() != null) {
            log.info("Update service provider");
        } else {
            log.info("Create service provider");
        }
        return serviceProviderRepository.save(saveServiceProvider);
    }

    @Override
    public ServiceProviderResponseDTO findServiceProviderByID(Long serviceProviderNo) {
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(serviceProviderNo);
        return serviceProvider.map(serviceProviderResponseDTOMapper::toDto).orElse(null);
    }
}
