package com.hias.mapper.request;

import com.hias.constant.CommonConstant;
import com.hias.entity.BenefitItem;
import com.hias.mapper.EntityMapper;
import com.hias.model.request.BenefitItemRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface BenefitItemRequestDTOMapper extends EntityMapper<BenefitItemRequestDTO, BenefitItem> {
}
