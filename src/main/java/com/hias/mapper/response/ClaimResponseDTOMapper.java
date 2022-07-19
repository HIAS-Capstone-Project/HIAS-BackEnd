package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Claim;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.ClaimResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface ClaimResponseDTOMapper extends EntityMapper<ClaimResponseDTO, Claim> {
}
