package com.hias.mapper;

import com.hias.entity.Policy;
import com.hias.model.request.PolicyRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PolicyRequestDTOMapper extends EntityMapper<PolicyRequestDTO, Policy> {
}
