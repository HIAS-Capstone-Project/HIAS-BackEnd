package com.hias.mapper;

import com.hias.entity.ServiceProvider;
import com.hias.model.request.ServiceProviderRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceProviderRequestDTOMapper extends EntityMapper<ServiceProviderRequestDTO, ServiceProvider> {
}
