package com.hias.service.impl;

import com.hias.entity.EmploymentType;
import com.hias.mapper.response.EmploymentTypeResponseDTOMapper;
import com.hias.model.response.EmploymentTypeResponseDTO;
import com.hias.repository.EmploymentTypeRepository;
import com.hias.service.EmploymentTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class EmploymentTypeServiceImpl implements EmploymentTypeService {
    private final EmploymentTypeRepository employmentTypeRepository;

    private final EmploymentTypeResponseDTOMapper employmentTypeResponseDTOMapper;

    @Override
    public List<EmploymentTypeResponseDTO> findAll() {
        List<EmploymentTypeResponseDTO> employmentTypeResponseDTOS = new ArrayList<>();
        List<EmploymentType> employmentTypes = employmentTypeRepository.findAll();
        if (CollectionUtils.isNotEmpty(employmentTypes)) {
            log.info("[findAll] Found {} employment type.", employmentTypes.size());
            employmentTypeResponseDTOS = employmentTypeResponseDTOMapper.toDtoList(employmentTypes);
        }
        return employmentTypeResponseDTOS;
    }
}
