package com.hias.mapper;


import com.hias.entity.Member;
import com.hias.model.request.MemberRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberRequestDTOMapper extends EntityMapper<MemberRequestDTO, Member>{
}
