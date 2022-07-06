package com.hias.mapper;

import com.hias.entity.Client;
import com.hias.model.response.ClientResponeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientResponeDTOMapper extends EntityMapper<ClientResponeDTO, Client> {
}
