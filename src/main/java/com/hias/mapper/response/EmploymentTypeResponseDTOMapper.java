package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.EmploymentType;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.EmploymentTypeResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface EmploymentTypeResponseDTOMapper extends EntityMapper<EmploymentTypeResponseDTO, EmploymentType> {
}
