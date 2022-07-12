package com.hias.service.impl;


import com.hias.entity.Bank;
import com.hias.entity.Client;
import com.hias.entity.Member;
import com.hias.entity.Policy;
import com.hias.mapper.MemberRequestDTOMapper;
import com.hias.mapper.MemberResponseDTOMapper;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.repository.MemberRepository;
import com.hias.service.MemberService;
import com.hias.utilities.DirectionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberResponseDTOMapper memberResponseDTOMapper;
    private final MemberRequestDTOMapper memberRequestDTOMapper;

    @Override
    public List<MemberResponseDTO> findMember(String key, Integer pageIndex, Integer pageSize, String[] sort) {
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
        return memberResponseDTOMapper.toDtoList(memberRepository.findByMember(key, pageable).toList());
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
    public Member saveMember(MemberRequestDTO memberRequestDTO) {
        Member saveMem = memberRequestDTOMapper.convert(memberRequestDTO, Bank.builder().bankNo(memberRequestDTO.getBankNo()).build()
                , Policy.builder().policyNo(memberRequestDTO.getPolicyNo()).build(), Client.builder().clientNo(memberRequestDTO.getClientNo()).build());
        if (memberRequestDTO.getMemberNo() != null) {
            log.info("Update member");
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
