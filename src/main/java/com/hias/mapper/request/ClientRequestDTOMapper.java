package com.hias.mapper.request;

import com.hias.constant.CommonConstant;
import com.hias.entity.Client;
import com.hias.mapper.EntityMapper;
import com.hias.model.request.ClientRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface ClientRequestDTOMapper extends EntityMapper<ClientRequestDTO, Client> {
}
