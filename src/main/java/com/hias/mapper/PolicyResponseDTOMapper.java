package com.hias.mapper;

import com.hias.entity.Policy;
import com.hias.model.response.PolicyResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PolicyResponseDTOMapper extends EntityMapper<PolicyResponseDTO, Policy> {
}
