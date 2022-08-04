package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Department;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.DepartmentResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface DepartmentResponseDTOMapper extends EntityMapper<DepartmentResponseDTO, Department> {
}
