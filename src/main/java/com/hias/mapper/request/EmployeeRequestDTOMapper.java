package com.hias.mapper.request;


import com.hias.constant.CommonConstant;
import com.hias.entity.Employee;
import com.hias.mapper.EntityMapper;
import com.hias.model.request.EmployeeRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = CommonConstant.SPRING)
public interface EmployeeRequestDTOMapper extends EntityMapper<EmployeeRequestDTO, Employee> {
    @Override
    @Mapping(source = "departmentNo", target = "department.departmentNo")
    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);
}
