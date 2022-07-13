package com.hias.mapper;


import com.hias.entity.Bank;
import com.hias.entity.Client;
import com.hias.entity.Member;
import com.hias.entity.Policy;
import com.hias.model.request.MemberRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberRequestDTOMapper extends EntityMapper<MemberRequestDTO, Member>{
    @Mapping(source = "memberRequestDTO.memberNo", target = "memberNo")
    @Mapping(source = "memberRequestDTO.memberName", target = "memberName")
    @Mapping(source = "memberRequestDTO.staffID", target = "staffID")
    @Mapping(source = "memberRequestDTO.phoneNumber", target = "phoneNumber")
    @Mapping(source = "memberRequestDTO.email", target = "email")
    @Mapping(source = "memberRequestDTO.bankAccountNo", target = "bankAccountNo")
    @Mapping(source = "memberRequestDTO.clientNo", target = "clientNo")
    @Mapping(source = "memberRequestDTO.policyNo", target = "policyNo")
    @Mapping(source = "memberRequestDTO.bankNo", target = "bankNo")
    @Mapping(source = "bank", target = "bank")
    @Mapping(source = "policy", target = "policy")
    @Mapping(source = "client", target = "client")
    Member convert(MemberRequestDTO memberRequestDTO, Bank bank, Policy policy, Client client);
}
