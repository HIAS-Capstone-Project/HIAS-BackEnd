package com.hias.mapper.response;

import com.hias.constant.CommonConstant;
import com.hias.entity.Bank;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.BankResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = CommonConstant.SPRING)
public interface BankResponseDTOMapper extends EntityMapper<BankResponseDTO, Bank> {
}
