package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Benefit;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.BenefitResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface BenefitResponseDTOMapper extends EntityMapper<BenefitResponseDTO, Benefit> {
}
