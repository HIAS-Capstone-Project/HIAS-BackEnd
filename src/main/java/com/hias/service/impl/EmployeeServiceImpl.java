package com.hias.service.impl;


import com.hias.entity.Employee;
import com.hias.exception.HIASException;
import com.hias.mapper.request.EmployeeRequestDTOMapper;
import com.hias.mapper.response.EmployeeResponseDTOMapper;
import com.hias.model.request.EmployeeRequestDTO;
import com.hias.model.response.EmployeeResponseDTO;
import com.hias.model.response.PagingResponse;
import com.hias.repository.EmployeeRepository;
import com.hias.service.EmployeeService;
import com.hias.utilities.DirectionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeResponseDTOMapper employeeResponseDTOMapper;
    private final EmployeeRequestDTOMapper employeeRequestDTOMapper;

    @Override
    public PagingResponse findEmployee(String key, Integer pageIndex, Integer pageSize, String[] sort) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 5 : pageSize;
        key = key == null ? "" : key;
        List<Sort.Order> orders = new ArrayList<>();
        String[] analyst;
        if (sort[0].contains(",")) {
            for (String s : sort) {
                analyst = s.split(",");
                orders.add(new Sort.Order(DirectionUtils.getDirection(analyst[1]), analyst[0]));
            }
        } else {
            orders.add(new Sort.Order(DirectionUtils.getDirection(sort[1]), sort[0]));
        }
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.by(orders));
        Page<Employee> page = employeeRepository.findEmployee(key, pageable);
        return new PagingResponse(employeeResponseDTOMapper.toDtoList(page.toList()), pageIndex, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteEmployeeByEmployeeNo(Long employeeNo) throws HIASException {
        Optional<Employee> employee = employeeRepository.findById(employeeNo);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            employee1.setDeleted(Boolean.TRUE);
            employeeRepository.save(employee1);
            log.info("[delete] Delete employee with employeeNo: {}", employeeNo);
        } else {
            throw HIASException.buildHIASException("Employee not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Employee saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee saveEmp = employeeRequestDTOMapper.toEntity(employeeRequestDTO);
        if (employeeRequestDTO.getEmployeeNo() != null) {
            log.info("[update] Update employee with employeeNo: {}", employeeRequestDTO.getEmployeeNo());
        } else {
            log.info("[create] Create employee");
        }
        return employeeRepository.save(saveEmp);
    }

    @Override
    public EmployeeResponseDTO findEmployeeByEmployeeNo(Long employeeNo) {
        Optional<Employee> employee = employeeRepository.findById(employeeNo);
        return employee.map(employeeResponseDTOMapper::toDto).orElse(null);
    }
}
