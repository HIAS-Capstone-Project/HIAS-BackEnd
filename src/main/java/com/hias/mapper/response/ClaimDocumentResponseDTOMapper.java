package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.ClaimDocument;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.ClaimDocumentResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface ClaimDocumentResponseDTOMapper extends EntityMapper<ClaimDocumentResponseDTO, ClaimDocument> {
}
