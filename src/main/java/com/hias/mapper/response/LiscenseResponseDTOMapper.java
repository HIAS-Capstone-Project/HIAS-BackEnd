package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.License;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.LicenseResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface LiscenseResponseDTOMapper extends EntityMapper<LicenseResponseDTO, License> {
}
