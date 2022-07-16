package com.hias.mapper.response;

import com.hias.entity.ServiceProvider;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.ServiceProviderResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceProviderResponseDTOMapper extends EntityMapper<ServiceProviderResponseDTO, ServiceProvider> {
}
