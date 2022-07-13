package com.hias.mapper;

import com.hias.entity.ServiceProvider;
import com.hias.model.response.ServiceProviderResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceProviderResponseDTOMapper extends EntityMapper<ServiceProviderResponseDTO, ServiceProvider> {
}
