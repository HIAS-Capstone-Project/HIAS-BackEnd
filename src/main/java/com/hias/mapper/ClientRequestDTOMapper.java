package com.hias.mapper;

import com.hias.entity.Client;
import com.hias.model.request.ClientRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientRequestDTOMapper extends EntityMapper<ClientRequestDTO, Client> {
}
