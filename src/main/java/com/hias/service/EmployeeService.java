package com.hias.service;

import com.hias.entity.Employee;
import com.hias.exception.HIASException;
import com.hias.model.request.EmployeeRequestDTO;
import com.hias.model.response.EmployeeResponseDTO;
import com.hias.model.response.PagingResponse;

public interface EmployeeService {
    PagingResponse findEmployee(String key, Integer pageIndex, Integer pageSize, String[] sort);

    void deleteEmployeeByEmployeeNo(Long employeeNo) throws HIASException;

    Employee saveEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO findEmployeeByEmployeeNo(Long employeeNo);
}
