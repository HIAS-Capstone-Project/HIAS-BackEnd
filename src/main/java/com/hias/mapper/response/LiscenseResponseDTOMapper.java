package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Liscense;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.LiscenseResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface LiscenseResponseDTOMapper extends EntityMapper<LiscenseResponseDTO, Liscense> {
}
