package com.hias.service.impl;


import com.hias.constant.ErrorMessageCode;
import com.hias.entity.Member;
import com.hias.exception.HIASException;
import com.hias.mapper.request.MemberRequestDTOMapper;
import com.hias.mapper.response.MemberResponseDTOMapper;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.model.response.PagingResponse;
import com.hias.repository.MemberRepository;
import com.hias.service.MemberService;
import com.hias.utilities.DirectionUtils;
import com.hias.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberResponseDTOMapper memberResponseDTOMapper;
    private final MemberRequestDTOMapper memberRequestDTOMapper;
    private final MessageUtils messageUtils;

    @Override
    public PagingResponse findMember(String key, Integer pageIndex, Integer pageSize, String[] sort) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 5 : pageSize;
        key = key == null ? "" : key;
        List<Sort.Order> orders = new ArrayList<>();
        String[] analyst;
        if (sort[0].contains(",")) {
            for (String s : sort) {
                analyst = s.split(",");
                orders.add(new Sort.Order(DirectionUtils.getDirection(analyst[1]), analyst[0]));
            }
        } else {
            orders.add(new Sort.Order(DirectionUtils.getDirection(sort[1]), sort[0]));
        }
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.by(orders));
        Page<Member> page = memberRepository.findMember(key, pageable);
        return new PagingResponse(memberResponseDTOMapper.toDtoList(page.toList()), pageIndex, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteMemberByMemberNo(Long memberNo) throws Exception {
        Optional<Member> member = memberRepository.findById(memberNo);
        if (member.isPresent()) {
            Member member1 = member.get();
            member1.setDeleted(Boolean.TRUE);
            memberRepository.save(member1);
            log.info("[delete] Delete member with memberNo: {}", memberNo);
        } else {
            throw new Exception("Member not found");
        }
    }

    @Override
    @Transactional
    public Member saveMember(MemberRequestDTO memberRequestDTO) throws DuplicateFormatFlagsException, HIASException {
        Member saveMem = memberRequestDTOMapper.toEntity(memberRequestDTO);
        if (memberRequestDTO.getMemberNo() != null) {
            log.info("[update] Update member with memberNo: {}", memberRequestDTO.getMemberNo());
        } else {
            if (memberRepository.findMemberByClientNo(memberRequestDTO.getClientNo()).stream().anyMatch(o -> memberRequestDTO.getStaffID().equals(o.getStaffID()))) {
                throw HIASException.buildHIASException("staffID", messageUtils.getMessage(ErrorMessageCode.STAFF_ID_EXISTENCE), HttpStatus.NOT_ACCEPTABLE);
            }
            saveMem.setHealthCardNo(UUID.randomUUID().toString());
            log.info("[create] Create member");
        }
        return memberRepository.save(saveMem);
    }

    @Override
    public MemberResponseDTO findMemberByMemberNo(Long memberNo) {
        Optional<Member> member = memberRepository.findById(memberNo);
        return member.map(memberResponseDTOMapper::toDto).orElse(null);
    }
}
