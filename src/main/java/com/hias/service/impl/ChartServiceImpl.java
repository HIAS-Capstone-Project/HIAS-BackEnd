package com.hias.service.impl;

import com.hias.constant.ChartConstant;
import com.hias.entity.Member;
import com.hias.model.response.ChartResponseDTO;
import com.hias.model.response.StatisticDTO;
import com.hias.repository.MemberRepository;
import com.hias.security.dto.UserDetail;
import com.hias.service.ChartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ChartServiceImpl implements ChartService {
    private MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ChartResponseDTO findMemberAgeChart(Long clientNo) {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        List<Member> memberList;
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            memberList = memberRepository.findMemberByClientNoAndIsDeletedIsFalse(clientNo);
        } else {
            if (clientNo != null) {
                memberList = memberRepository.findMemberByClientNoAndIsDeletedIsFalse(clientNo);
            } else {
                memberList = memberRepository.findByIsDeletedIsFalse();
            }
        }
        List<StatisticDTO> statistics = new ArrayList<>();
        statistics.add(new StatisticDTO("<30", memberList.stream().filter(o -> LocalDate.now().minusYears(o.getDob().getYear()).getYear() < 30).count()));
        statistics.add(new StatisticDTO(">50", memberList.stream().filter(o -> LocalDate.now().minusYears(o.getDob().getYear()).getYear() > 50).count()));
        statistics.add(new StatisticDTO("30<= <=50", memberList.stream().filter(o -> LocalDate.now().minusYears(o.getDob().getYear()).getYear() >= 30 &&
                LocalDate.now().minusYears(o.getDob().getYear()).getYear() <= 50).count()));
        return ChartResponseDTO.builder().chartType(ChartConstant.PIE_CHART).statistics(statistics).build();
    }

    @Override
    @Transactional
    public ChartResponseDTO findMemberLocationChart(Long clientNo) {
        log.info("Starting Transaction");
        String x = "SELECT COUNT(member_name), province_name "+
                "FROM HIAS.DISTRICT d INNER JOIN HIAS.PROVINCE p ON d.province_no = p.province_no " +
                "INNER JOIN HIAS.MEMBER m ON d.district_no = m.district_no " +
                "WHERE 1 = 1 %s" +
                "GROUP BY province_name";
        if(clientNo != null){
            x = String.format(x, String.format("AND m.client_no = %s", clientNo));
        }else {
            x = String.format(x, "");
        }
        List<StatisticDTO> statisticDTOS = entityManager.createQuery(String.valueOf(x)).getResultList();
        return ChartResponseDTO.builder().chartType(ChartConstant.BAR_CHART).statistics(statisticDTOS).build();
    }

    @Override
    public ChartResponseDTO findMemberGenderChart(Long clientNo) {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        List<Member> memberList;
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            memberList = memberRepository.findMemberByClientNoAndIsDeletedIsFalse(clientNo);
        } else {
            if (clientNo != null) {
                memberList = memberRepository.findMemberByClientNoAndIsDeletedIsFalse(clientNo);
            } else {
                memberList = memberRepository.findByIsDeletedIsFalse();
            }
        }
        List<StatisticDTO> statistics = new ArrayList<>();
        statistics.add(new StatisticDTO("Male", memberList.stream().filter(o -> "M".equalsIgnoreCase(o.getGenderEnum().getCode())).count()));
        statistics.add(new StatisticDTO("Female", memberList.stream().filter(o -> "F".equalsIgnoreCase(o.getGenderEnum().getCode())).count()));
        return ChartResponseDTO.builder().chartType(ChartConstant.BAR_CHART).statistics(statistics).build();
    }
}
