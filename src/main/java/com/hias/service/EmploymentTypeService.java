package com.hias.service;

import com.hias.model.response.EmploymentTypeResponseDTO;

import java.util.List;

public interface EmploymentTypeService {
    List<EmploymentTypeResponseDTO> findAll();
}
