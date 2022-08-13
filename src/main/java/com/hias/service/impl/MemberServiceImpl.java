package com.hias.service.impl;


import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.entity.HealthCardFormat;
import com.hias.entity.Member;
import com.hias.exception.HIASException;
import com.hias.mapper.request.MemberRequestDTOMapper;
import com.hias.mapper.response.MemberResponseDTOMapper;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.model.response.PagingResponse;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.HealthCardFormatRepository;
import com.hias.repository.MemberRepository;
import com.hias.service.MemberService;
import com.hias.utilities.DirectionUtils;
import com.hias.utils.DateUtils;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.MemberValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final HealthCardFormatRepository healthCardFormatRepository;
    private final MemberResponseDTOMapper memberResponseDTOMapper;
    private final MemberRequestDTOMapper memberRequestDTOMapper;
    private final MessageUtils messageUtils;
    private final MemberValidator memberValidator;

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
    public PagingResponseModel<MemberResponseDTO> search(String searchValue, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Member> memberPage = memberRepository.findAllBySearchValue(searchValue, pageable);

        if (!memberPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Member> members = memberPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", members.size(), searchValue);

        List<MemberResponseDTO> memberResponseDTOS = memberResponseDTOMapper.toDtoList(members);

        return new PagingResponseModel<>(new PageImpl<>(memberResponseDTOS,
                pageable,
                memberPage.getTotalElements()));
    }

    @Override
    public MemberResponseDTO searchByHealthCardNo(String healthCardNo, LocalDate visitDate) throws HIASException {
        List<Member> members = memberRepository.searchByHealthCardNo(healthCardNo, visitDate);
        if (CollectionUtils.isEmpty(members)) {
            throw HIASException.buildHIASException(messageUtils.getMessage(ErrorMessageCode.HEALCARD_NO_NOT_EXISTENCE,
                    healthCardNo,
                    DateUtils.formatDate(visitDate, "dd-MM-yyyy")), HttpStatus.NOT_ACCEPTABLE);
        }
        MemberResponseDTO memberResponseDTO = memberResponseDTOMapper.toDto(members.get(0));

        return memberResponseDTO;
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
    @Transactional
    public MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO) throws HIASException {
        String staffID = memberRequestDTO.getStaffID();
        Long clientNo = memberRequestDTO.getClientNo();
        log.info("[createMember] Start create new member.");
        if (memberValidator.isStaffIDExistanceInSameClient(clientNo, staffID)) {
            throw HIASException.buildHIASException(FieldNameConstant.STAFF_ID,
                    messageUtils.getMessage(ErrorMessageCode.STAFF_ID_EXISTENCE),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        Member member = memberRepository.save(memberRequestDTOMapper.toEntity(memberRequestDTO));
        //create healcard no
        Long memberNo = member.getMemberNo();
        log.info("[createMember] Start create heathcardNo for member : {}", memberNo);
        List<HealthCardFormat> healthCardFormats = healthCardFormatRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
        if (CollectionUtils.isNotEmpty(healthCardFormats)) {
            member.setHealthCardNo(healthCardFormats.get(0).getPrefix() + memberNo);
            memberRepository.save(member);
        }

        MemberResponseDTO memberResponseDTO = memberResponseDTOMapper.toDto(member);

        return memberResponseDTO;
    }

    @Override
    @Transactional
    public MemberResponseDTO updateMember(MemberRequestDTO memberRequestDTO) throws HIASException {
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        Long memberNo = memberRequestDTO.getMemberNo();
        Optional<Member> memberOptional = memberRepository.findByMemberNoAndIsDeletedIsFalse(memberNo);
        if (!memberOptional.isPresent()) {
            log.info("[updateMember] Cannot found member with memberNo : {} in the system.", memberNo);
        } else {
            Member member = memberRequestDTOMapper.toEntity(memberRequestDTO);
            memberResponseDTO = memberResponseDTOMapper.toDto(memberRepository.save(member));
            log.info("[update] Updated member with memberNo : {} in the system.", memberNo);
        }
        return memberResponseDTO;
    }

    @Override
    public MemberResponseDTO findMemberByMemberNo(Long memberNo) {
        Optional<Member> member = memberRepository.findById(memberNo);
        return member.map(memberResponseDTOMapper::toDto).orElse(null);
    }
}
