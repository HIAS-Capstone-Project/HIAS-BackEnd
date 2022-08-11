package com.hias.service.impl;

import com.hias.entity.Province;
import com.hias.mapper.response.DistrictResponseDTOMapper;
import com.hias.mapper.response.ProvinceResponseDTOMapper;
import com.hias.model.response.ProvinceResponseDTO;
import com.hias.repository.ProvinceRepository;
import com.hias.service.ProvinceService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceResponseDTOMapper provinceResponseDTOMapper;
    private final DistrictResponseDTOMapper districtResponseDTOMapper;

    @Override
    public List<ProvinceResponseDTO> findAll() {
        List<Province> provinces = provinceRepository.findAllByIsDeletedIsFalse();
        List<ProvinceResponseDTO> provinceResponseDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(provinces)) {

            for (Province province : provinces) {
                ProvinceResponseDTO provinceResponseDTO = provinceResponseDTOMapper.toDto(province);
                provinceResponseDTO.setDistrictResponseDTOS(districtResponseDTOMapper.toDtoList(province.getDistricts()));
                provinceResponseDTOS.add(provinceResponseDTO);
            }
        }
        return provinceResponseDTOS;
    }

    @Override
    public ProvinceResponseDTO findByProvinceNo(Long provinceNo) {
        ProvinceResponseDTO provinceResponseDTO = new ProvinceResponseDTO();
        Optional<Province> optionalProvince = provinceRepository.findProvinceByProvinceNoAndIsDeletedIsFalse(provinceNo);
        if (optionalProvince.isPresent()) {
            provinceResponseDTO = provinceResponseDTOMapper.toDto(optionalProvince.get());
            provinceResponseDTO.setDistrictResponseDTOS(districtResponseDTOMapper.toDtoList(optionalProvince.get().getDistricts()));
        }
        return provinceResponseDTO;
    }
}
