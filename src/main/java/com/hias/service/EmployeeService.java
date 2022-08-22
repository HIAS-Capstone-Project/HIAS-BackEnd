package com.hias.service;

import com.hias.entity.Employee;
import com.hias.exception.HIASException;
import com.hias.model.request.EmployeeRequestDTO;
import com.hias.model.response.EmployeeResponseDTO;
import com.hias.model.response.PagingResponse;
import com.hias.model.response.PagingResponseModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    PagingResponse findEmployee(String key, Integer pageIndex, Integer pageSize, String[] sort);

    PagingResponseModel<EmployeeResponseDTO> search(String searchValue, Pageable pageable);

    List<EmployeeResponseDTO> findByEmploymentTypeCode(String employmentTypeCode);

    void deleteEmployeeByEmployeeNo(Long employeeNo) throws HIASException;

    Employee saveEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) throws HIASException;

    EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO findEmployeeByEmployeeNo(Long employeeNo);
}
