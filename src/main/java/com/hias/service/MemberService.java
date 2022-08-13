package com.hias.service;

import com.hias.entity.Member;
import com.hias.exception.HIASException;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.model.response.PagingResponse;
import com.hias.model.response.PagingResponseModel;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface MemberService {
    PagingResponse findMember(String key, Integer pageIndex, Integer pageSize, String[] sort);

    PagingResponseModel<MemberResponseDTO> search(String searchValue, Pageable pageable);

    MemberResponseDTO searchByHealthCardNo(String searchValue, LocalDate visitDate) throws HIASException;

    void deleteMemberByMemberNo(Long memberNo) throws Exception;

    Member saveMember(MemberRequestDTO member) throws HIASException;

    MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO) throws HIASException;

    MemberResponseDTO updateMember(MemberRequestDTO memberRequestDTO) throws HIASException;

    MemberResponseDTO findMemberByMemberNo(Long memberNo);
}
