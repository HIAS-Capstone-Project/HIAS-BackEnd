package com.hias.service;

import com.hias.model.response.DistrictResponseDTO;

import java.util.List;

public interface DistrictService {

    List<DistrictResponseDTO> findByProvinceNo(Long provinceNo);

}
