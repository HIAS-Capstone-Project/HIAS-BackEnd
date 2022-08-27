package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.ClaimRemarkHistory;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.ClaimRemarkHistoryResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface ClaimRemarkHistoryResponseDTOMapper extends EntityMapper<ClaimRemarkHistoryResponseDTO, ClaimRemarkHistory> {
}
