package com.hias.mapper.request;

import com.hias.constant.CommonConstant;
import com.hias.entity.Policy;
import com.hias.mapper.EntityMapper;
import com.hias.model.request.PolicyRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = CommonConstant.SPRING)
public interface PolicyRequestDTOMapper extends EntityMapper<PolicyRequestDTO, Policy> {

    @Override
    @Mapping(source = "clientNo", target = "client.clientNo")
    Policy toEntity(PolicyRequestDTO dto);
}
