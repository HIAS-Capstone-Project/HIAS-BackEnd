package com.hias.mapper.request;

import com.hias.constant.CommonConstant;
import com.hias.entity.Benefit;
import com.hias.mapper.EntityMapper;
import com.hias.model.request.BenefitRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface BenefitRequestDTOMapper extends EntityMapper<BenefitRequestDTO, Benefit> {
}
