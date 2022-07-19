package com.hias.service.impl;

import com.hias.constant.ErrorMessageCode;
import com.hias.entity.ServiceProvider;
import com.hias.exception.HIASException;
import com.hias.mapper.request.ServiceProviderRequestDTOMapper;
import com.hias.mapper.response.ServiceProviderResponseDTOMapper;
import com.hias.model.request.ServiceProviderRequestDTO;
import com.hias.model.response.PagingResponse;
import com.hias.model.response.ServiceProviderResponseDTO;
import com.hias.repository.ServiceProviderRepository;
import com.hias.service.ServiceProviderService;
import com.hias.utilities.DirectionUtils;
import com.hias.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MessageUtils messageUtils;

    @Override
    public PagingResponse findServiceProvider(String key, Integer pageIndex, Integer pageSize, String[] sort) {
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
        Page<ServiceProvider> page = serviceProviderRepository.findServiceProvider(key, pageable);
        return new PagingResponse(serviceProviderResponseDTOMapper.toDtoList(page.toList()), pageIndex, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteServiceProviderByID(Long serviceProviderNo) throws Exception {
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(serviceProviderNo);
        if (serviceProvider.isPresent()) {
            ServiceProvider serviceProvider1 = serviceProvider.get();
            serviceProvider1.setDeleted(Boolean.TRUE);
            serviceProviderRepository.save(serviceProvider1);
            log.info("[delete] Delete service provider with serviceProviderNo: {}", serviceProviderNo);
        } else {
            throw new Exception("ServiceProvider not found");
        }
    }

    @Override
    @Transactional
    public ServiceProvider saveServiceProvider(ServiceProviderRequestDTO serviceProviderRequestDTO) throws HIASException {
        ServiceProvider saveServiceProvider = serviceProviderRequestDTOMapper.toEntity(serviceProviderRequestDTO);
        if (serviceProviderRequestDTO.getServiceProviderNo() != null) {
            log.info("[update] Update service provider with serviceProviderNo: {}", serviceProviderRequestDTO.getServiceProviderNo());
        } else {
            if (serviceProviderRepository.findAll().stream().anyMatch(o -> o.getServiceProviderID().equals(saveServiceProvider.getServiceProviderID()))) {
                throw HIASException.buildHIASException("serviceProviderID",
                        messageUtils.getMessage(ErrorMessageCode.SERVICE_PROVIDER_ID_EXISTENCE)
                        , HttpStatus.NOT_ACCEPTABLE);
            }
            log.info("[create] Create service provider");
        }
        return serviceProviderRepository.save(saveServiceProvider);
    }

    @Override
    public ServiceProviderResponseDTO findServiceProviderByID(Long serviceProviderNo) {
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(serviceProviderNo);
        return serviceProvider.map(serviceProviderResponseDTOMapper::toDto).orElse(null);
    }
}
