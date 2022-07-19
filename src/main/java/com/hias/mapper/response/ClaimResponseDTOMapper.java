package com.hias.mapper.response;

import com.hias.entity.Claim;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.ClaimResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ClaimResponseDTOMapper extends EntityMapper<ClaimResponseDTO, Claim> {
}
