package com.hias.mapper;


import com.hias.entity.Member;
import com.hias.model.request.MemberRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberRequestDTOMapper extends EntityMapper<MemberRequestDTO, Member>{
    @Override
    @Mapping(source = "memberNo", target = "memberNo")
    @Mapping(source = "memberName", target = "memberName")
    @Mapping(source = "staffID", target = "staffID")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "healthCardNo", target = "healthCardNo")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "bankAccountNo", target = "bankAccountNo")
    @Mapping(source = "clientNo", target = "client.clientNo")
    @Mapping(source = "policyNo", target = "policy.policyNo")
    @Mapping(source = "bankNo", target = "bank.bankNo")
    Member toEntity(MemberRequestDTO memberRequestDTO);
}
