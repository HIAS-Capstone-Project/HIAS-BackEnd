package com.hias.service;

import com.hias.model.response.ProvinceResponseDTO;

import java.util.List;

public interface ProvinceService {

    List<ProvinceResponseDTO> findAll();

    ProvinceResponseDTO findByProvinceNo(Long provinceNo);
}
