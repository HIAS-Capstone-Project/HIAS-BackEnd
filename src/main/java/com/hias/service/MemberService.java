package com.hias.service;

import com.hias.entity.Member;
import com.hias.exception.HIASException;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.model.response.PagingResponse;

public interface MemberService {
    PagingResponse findMember(String key, Integer pageIndex, Integer pageSize, String[] sort);

    void deleteMemberByMemberNo(Long memberNo) throws Exception;

    Member saveMember(MemberRequestDTO member) throws HIASException;

    MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO) throws HIASException;

    MemberResponseDTO findMemberByMemberNo(Long memberNo);
}
