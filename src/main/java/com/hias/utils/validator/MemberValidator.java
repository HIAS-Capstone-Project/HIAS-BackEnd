package com.hias.utils.validator;

import com.hias.entity.Member;
import com.hias.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public boolean isStaffIDExistanceInSameClient(Long clientNo, String staffID) {
        List<Member> members = memberRepository.findByClientNoAndStaffIDIgnoreCaseAndIsDeletedIsFalse(clientNo, staffID);
        if (CollectionUtils.isNotEmpty(members)) {
            log.error("[isStaffIDExistanceInSameClient] StaffID : {} has already exist for clientNo :{}.", staffID, clientNo);
            return true;
        }
        return false;
    }
}
