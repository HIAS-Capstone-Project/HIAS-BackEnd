package com.hias.service;

import com.hias.entity.Member;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;

import java.util.List;

public interface MemberService {
    List<MemberResponseDTO> findMember(String key, Integer pageIndex, Integer pageSize, String[] sort);

    void deleteMemberByMemberNo(Long memberNo) throws Exception;

    Member saveMember(MemberRequestDTO member);

    MemberResponseDTO findMemberByMemberNo(Long memberNo);
}
