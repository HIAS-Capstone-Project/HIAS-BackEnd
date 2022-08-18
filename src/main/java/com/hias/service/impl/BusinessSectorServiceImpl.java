package com.hias.service.impl;

import com.hias.entity.BusinessSector;
import com.hias.mapper.response.BusinessSectorResponseDTOMapper;
import com.hias.model.response.BusinessSectorResponseDTO;
import com.hias.repository.BusinessSectorRepository;
import com.hias.service.BusinessSectorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BusinessSectorServiceImpl implements BusinessSectorService {
    private final BusinessSectorRepository businessSectorRepository;
    private final BusinessSectorResponseDTOMapper businessSectorResponseDTOMapper;

    @Override
    public List<BusinessSectorResponseDTO> findAll() {
        List<BusinessSectorResponseDTO> businessSectorResponseDTOS = new ArrayList<>();
        List<BusinessSector> businessSectors = businessSectorRepository.findAllByIsDeletedIsFalse();
        if (CollectionUtils.isNotEmpty(businessSectors)) {
            businessSectorResponseDTOS = businessSectorResponseDTOMapper.toDtoList(businessSectors);
        }
        return businessSectorResponseDTOS;
    }
}
