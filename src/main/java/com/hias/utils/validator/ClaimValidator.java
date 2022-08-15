package com.hias.utils.validator;

import com.hias.constant.StatusCode;
import com.hias.entity.Claim;
import com.hias.entity.Member;
import com.hias.repository.ClaimRepository;
import com.hias.repository.MemberRepository;
import com.hias.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class ClaimValidator {

    private final MemberRepository memberRepository;
    private final ClaimRepository claimRepository;

    public boolean isValidBenefitPeriod(Long memberNo, LocalDate visitDate) {
        Optional<Member> memberOptional = memberRepository.findByMemberNoAndIsDeletedIsFalse(memberNo);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            LocalDate startDate = member.getStartDate();
            LocalDate endDate = member.getEndDate();
            return DateUtils.isDateBetween(startDate, visitDate, endDate);
        }
        return false;
    }

    public boolean isDraftClaim(Long claimNo) {
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            return StatusCode.DRAFT.equals(claim.getStatusCode());
        }
        return false;
    }

}
