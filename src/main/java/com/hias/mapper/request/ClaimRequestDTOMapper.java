package com.hias.mapper.request;

import com.hias.constant.CommonConstant;
import com.hias.entity.Claim;
import com.hias.mapper.EntityMapper;
import com.hias.model.request.ClaimRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = CommonConstant.SPRING)
public interface ClaimRequestDTOMapper extends EntityMapper<ClaimRequestDTO, Claim> {

    @Override
    @Mapping(source = "memberNo", target = "member.memberNo")
    @Mapping(source = "benefitItemNo", target = "benefitItem.benefitItemNo")
    Claim toEntity(ClaimRequestDTO dto);
}
