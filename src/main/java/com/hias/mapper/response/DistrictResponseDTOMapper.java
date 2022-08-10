package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.District;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.DistrictResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface DistrictResponseDTOMapper extends EntityMapper<DistrictResponseDTO, District> {
}
