package com.hias.service;

import com.hias.model.response.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {

    List<DepartmentResponseDTO> findAll();

}
