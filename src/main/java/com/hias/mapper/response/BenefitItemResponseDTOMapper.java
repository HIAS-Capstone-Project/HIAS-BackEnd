package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.BenefitItem;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.BenefitItemResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface BenefitItemResponseDTOMapper extends EntityMapper<BenefitItemResponseDTO, BenefitItem> {
}
