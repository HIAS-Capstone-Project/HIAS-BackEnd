package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Policy;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.PolicyResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface PolicyResponseDTOMapper extends EntityMapper<PolicyResponseDTO, Policy> {
}
