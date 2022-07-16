package com.hias.mapper.response;


import com.hias.entity.Member;
import com.hias.mapper.EntityMapper;
import com.hias.model.response.MemberResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberResponseDTOMapper extends EntityMapper<MemberResponseDTO, Member> {
}
