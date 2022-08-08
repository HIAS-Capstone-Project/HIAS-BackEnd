package com.hias.service.impl;

import com.hias.entity.Department;
import com.hias.mapper.response.DepartmentResponseDTOMapper;
import com.hias.mapper.response.EmploymentTypeResponseDTOMapper;
import com.hias.model.response.DepartmentResponseDTO;
import com.hias.repository.DepartmentRepository;
import com.hias.repository.EmploymentTypeRepository;
import com.hias.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentResponseDTOMapper departmentResponseDTOMapper;
    private final EmploymentTypeResponseDTOMapper employmentTypeResponseDTOMapper;
    private final EmploymentTypeRepository employmentTypeRepository;

    @Override
    public List<DepartmentResponseDTO> findAll() {
        List<DepartmentResponseDTO> departmentResponseDTOS = new ArrayList<>();
        List<Department> departments = departmentRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(departments)) {
            departmentResponseDTOS = departmentResponseDTOMapper.toDtoList(departments);
            departmentResponseDTOS.forEach(o -> o.setList(employmentTypeResponseDTOMapper.toDtoList(departments.stream().filter(d -> Objects.equals(d.getDepartmentNo(), o.getDepartmentNo())).
                    findFirst().get().getDepartmentEmploymentTypes().stream().map(de -> employmentTypeRepository.findByEmploymentTypeNo(de.getEmploymentTypeNo()).get()).collect(Collectors.toList()))));
        }
        return departmentResponseDTOS;
    }
}
