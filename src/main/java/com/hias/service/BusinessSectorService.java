package com.hias.service;

import com.hias.model.response.BusinessSectorResponseDTO;

import java.util.List;

public interface BusinessSectorService {
    List<BusinessSectorResponseDTO> findAll();
}
