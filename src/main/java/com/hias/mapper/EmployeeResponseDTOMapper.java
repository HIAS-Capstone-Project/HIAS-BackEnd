package com.hias.mapper;


import com.hias.entity.Employee;
import com.hias.model.response.EmployeeResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeResponseDTOMapper extends EntityMapper<EmployeeResponseDTO, Employee>{
}
