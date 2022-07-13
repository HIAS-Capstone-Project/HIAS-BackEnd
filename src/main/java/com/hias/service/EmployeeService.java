package com.hias.service;

import com.hias.entity.Employee;
import com.hias.model.request.EmployeeRequestDTO;
import com.hias.model.response.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDTO> findEmployee(String key, Integer pageIndex, Integer pageSize, String[] sort);

    void deleteEmployeeByEmployeeNo(Long employeeNo) throws Exception;

    Employee saveEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO findEmployeeByEmployeeNo(Long employeeNo);
}
