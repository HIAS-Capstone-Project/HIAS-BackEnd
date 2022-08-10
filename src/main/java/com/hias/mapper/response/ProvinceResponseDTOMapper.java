package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Province;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.ProvinceResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface ProvinceResponseDTOMapper extends EntityMapper<ProvinceResponseDTO, Province> {
}
