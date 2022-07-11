package com.hias.service.impl;


import com.hias.entity.Bank;
import com.hias.entity.Client;
import com.hias.entity.Member;
import com.hias.entity.Policy;
import com.hias.mapper.MemberResponseDTOMapper;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.repository.MemberRepository;
import com.hias.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberResponseDTOMapper memberResponseDTOMapper;

    @Override
    public List<MemberResponseDTO> findMember(String key, Integer pageIndex, Integer pageSize) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 5 : pageSize;
        key = key == null ? "" : key;
        Sort sort = Sort.by("modifiedOn").descending();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
        return memberResponseDTOMapper.toDtoList(memberRepository.findByMemberNameContainingAndIsDeletedIsFalseOrStaffIDContainingAndIsDeletedIsFalse(key, key, pageable).toList());
    }

    @Override
    public void deleteMemberByMemberNo(Long memberNo) throws Exception {
        Optional<Member> member = memberRepository.findById(memberNo);
        if (member.isPresent()) {
            Member member1 = member.get();
            member1.setDeleted(true);
            memberRepository.save(member1);
        } else {
            throw new Exception("Member not found");
        }
    }

    @Override
    public Member saveMember(MemberRequestDTO member) {
        Member saveMem = Member.builder().memberName(member.getMemberName()).staffID(member.getStaffID())
                .phoneNumber(member.getPhoneNumber()).email(member.getPhoneNumber()).email(member.getEmail())
                .bankAccountNo(member.getBankAccountNo()).clientNo(member.getClientNo()).policyNo(member.getPolicyNo())
                .bankNo(member.getBankNo()).bank(Bank.builder().bankNo(member.getBankNo()).build())
                .client(Client.builder().clientNo(member.getClientNo()).build())
                .policy(Policy.builder().policyNo(member.getPolicyNo()).build()).build();
        if (member.getMemberNo() != null) {
            log.info("Update member");
            saveMem.setMemberNo(member.getMemberNo());
        } else {
            log.info("Create member");
        }
        return memberRepository.save(saveMem);
    }

    @Override
    public MemberResponseDTO findMemberByMemberNo(Long memberNo) {
        Optional<Member> member = memberRepository.findById(memberNo);
        return member.map(memberResponseDTOMapper::toDto).orElse(null);
    }
}
