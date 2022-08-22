package com.hias.service.impl;


import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.entity.Employee;
import com.hias.exception.HIASException;
import com.hias.mapper.request.EmployeeRequestDTOMapper;
import com.hias.mapper.response.EmployeeResponseDTOMapper;
import com.hias.model.request.EmployeeRequestDTO;
import com.hias.model.response.EmployeeResponseDTO;
import com.hias.model.response.PagingResponse;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.EmployeeRepository;
import com.hias.service.EmployeeService;
import com.hias.utilities.DirectionUtils;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.EmployeeValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.*;
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

    private final EmployeeValidator employeeValidator;

    private final MessageUtils messageUtils;

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
    public PagingResponseModel<EmployeeResponseDTO> search(String searchValue, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Employee> employeePage = employeeRepository.findAllBySearchValue(searchValue, pageable);

        if (!employeePage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Employee> employees = employeePage.getContent();

        log.info("[search] Found {} elements match with value : {}.", employees.size(), searchValue);

        List<EmployeeResponseDTO> employeeResponseDTOS = employeeResponseDTOMapper.toDtoList(employees);

        return new PagingResponseModel<>(new PageImpl<>(employeeResponseDTOS,
                pageable,
                employeePage.getTotalElements()));
    }

    @Override
    public List<EmployeeResponseDTO> findByEmploymentTypeCode(String employmentTypeCode) {
        List<Employee> employees = employeeRepository.findByEmploymentTypeCode(employmentTypeCode);
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(employees)) {
            employeeResponseDTOS = employeeResponseDTOMapper.toDtoList(employees);
        }
        return employeeResponseDTOS;
    }

    @Override
    @Transactional
    public void deleteEmployeeByEmployeeNo(Long employeeNo) throws HIASException {
        Optional<Employee> employee = employeeRepository.findByEmployeeNoAndIsDeletedIsFalse(employeeNo);
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
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) throws HIASException {
        String employeeID = employeeRequestDTO.getEmployeeID();
        log.info("[create] Start create new employee.");
        if (employeeValidator.isEmployeeIDExistance(employeeID)) {
            throw HIASException.buildHIASException(FieldNameConstant.EMPLOYEE_ID,
                    messageUtils.getMessage(ErrorMessageCode.EMPLOYEE_ID_EXISTENCE)
                    , HttpStatus.NOT_ACCEPTABLE);
        }
        EmployeeResponseDTO employeeResponseDTO;
        Employee employee = employeeRepository.save(employeeRequestDTOMapper.toEntity(employeeRequestDTO));
        employeeResponseDTO = employeeResponseDTOMapper.toDto(employee);
        log.info("[create] End create new employee with employee no : {}", employee.getEmployeeNo());
        return employeeResponseDTO;
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        Long employeeNo = employeeRequestDTO.getEmployeeNo();
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeNoAndIsDeletedIsFalse(employeeNo);
        if (!employeeOptional.isPresent()) {
            log.info("[updateEmployee] Cannot found employee with employee no : {} in the system.", employeeNo);
        } else {
            Employee employee = employeeRequestDTOMapper.toEntity(employeeRequestDTO);
            employeeResponseDTO = employeeResponseDTOMapper.toDto(employeeRepository.save(employee));
            log.info("[update] Updated member with employeeNo : {} in the system.", employeeNo);
        }
        return employeeResponseDTO;
    }

    @Override
    public EmployeeResponseDTO findEmployeeByEmployeeNo(Long employeeNo) {
        Optional<Employee> employee = employeeRepository.findById(employeeNo);
        return employee.map(employeeResponseDTOMapper::toDto).orElse(null);
    }
}
