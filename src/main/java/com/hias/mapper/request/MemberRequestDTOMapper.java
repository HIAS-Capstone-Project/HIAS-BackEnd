package com.hias.mapper.request;


import com.hias.constant.CommonConstant;
import com.hias.entity.Member;
import com.hias.mapper.EntityMapper;
import com.hias.model.request.MemberRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = CommonConstant.SPRING)
public interface MemberRequestDTOMapper extends EntityMapper<MemberRequestDTO, Member> {
    @Override
    @Mapping(source = "clientNo", target = "client.clientNo")
    @Mapping(source = "policyNo", target = "policy.policyNo")
    @Mapping(source = "bankNo", target = "bank.bankNo")
    Member toEntity(MemberRequestDTO memberRequestDTO);
}
