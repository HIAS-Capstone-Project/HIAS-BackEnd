package com.hias.service.impl;

import com.hias.entity.District;
import com.hias.mapper.response.DistrictResponseDTOMapper;
import com.hias.model.response.DistrictResponseDTO;
import com.hias.repository.DistrictRepository;
import com.hias.service.DistrictService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictResponseDTOMapper districtResponseDTOMapper;

    @Override
    public List<DistrictResponseDTO> findByProvinceNo(Long provinceNo) {
        List<DistrictResponseDTO> districtResponseDTOS = new ArrayList<>();
        List<District> districts = districtRepository.findByProvinceNoAndIsDeletedIsFalse(provinceNo);
        if (CollectionUtils.isNotEmpty(districts)) {
            districtResponseDTOS = districtResponseDTOMapper.toDtoList(districts);
        }
        return districtResponseDTOS;
    }

    @Override
    public DistrictResponseDTO findByDistrictNo(Long districtNo) {
        Optional<District> optionalDistrict = districtRepository.findDistinctByDistrictNoAndIsDeletedIsFalse(districtNo);
        DistrictResponseDTO districtResponseDTO = new DistrictResponseDTO();
        if (optionalDistrict.isPresent()) {
            districtResponseDTO = districtResponseDTOMapper.toDto(optionalDistrict.get());
        }
        return districtResponseDTO;
    }
}
