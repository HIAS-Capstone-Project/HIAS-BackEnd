package com.hias.mapper;


import com.hias.entity.*;
import com.hias.model.request.EmployeeRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeRequestDTOMapper extends EntityMapper<EmployeeRequestDTO, Employee>{
    @Override
    @Mapping(source = "employeeID", target = "employeeID")
    @Mapping(source = "employeeName", target = "employeeName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "departmentNo", target = "department.departmentNo")
    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);
}
