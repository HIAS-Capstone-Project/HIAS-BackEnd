package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.BusinessSector;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.BusinessSectorResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface BusinessSectorResponseDTOMapper extends EntityMapper<BusinessSectorResponseDTO, BusinessSector> {
}
