package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Client;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.ClientResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface ClientResponeDTOMapper extends EntityMapper<ClientResponseDTO, Client> {
}
