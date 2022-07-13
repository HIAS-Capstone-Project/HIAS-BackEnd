package com.hias.mapper;


import com.hias.entity.*;
import com.hias.model.request.EmployeeRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeRequestDTOMapper extends EntityMapper<EmployeeRequestDTO, Employee>{
    @Mapping(source = "employeeRequestDTO.employeeID", target = "employeeID")
    @Mapping(source = "employeeRequestDTO.employeeName", target = "employeeName")
    @Mapping(source = "employeeRequestDTO.phoneNumber", target = "phoneNumber")
    @Mapping(source = "employeeRequestDTO.email", target = "email")
    @Mapping(source = "employeeRequestDTO.departmentNo", target = "departmentNo")
    @Mapping(source = "department", target = "department")
    Employee convert(EmployeeRequestDTO employeeRequestDTO, Department department);
}
