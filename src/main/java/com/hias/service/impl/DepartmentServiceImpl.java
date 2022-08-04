package com.hias.service.impl;

import com.hias.entity.Department;
import com.hias.mapper.response.DepartmentResponseDTOMapper;
import com.hias.model.response.DepartmentResponseDTO;
import com.hias.repository.DepartmentRepository;
import com.hias.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentResponseDTOMapper departmentResponseDTOMapper;

    @Override
    public List<DepartmentResponseDTO> findAll() {
        List<DepartmentResponseDTO> departmentResponseDTOS = new ArrayList<>();
        List<Department> departments = departmentRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(departments)) {
            departmentResponseDTOS = departmentResponseDTOMapper.toDtoList(departments);
        }
        return departmentResponseDTOS;
    }
}
