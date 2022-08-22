package com.hias.service.impl;

import com.hias.constant.ChartConstant;
import com.hias.constant.ChartQuery;
import com.hias.constant.RoleEnum;
import com.hias.entity.Member;
import com.hias.mapper.StatisticsRowMapper;
import com.hias.model.response.ChartResponseDTO;
import com.hias.model.response.LineChartResponseDTO;
import com.hias.model.response.SingleLineChartResponseDTO;
import com.hias.model.response.StatisticDTO;
import com.hias.repository.MemberRepository;
import com.hias.security.dto.UserDetail;
import com.hias.service.ChartService;
import com.hias.service.ClientService;
import com.hias.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ChartServiceImpl implements ChartService {
    private final MemberRepository memberRepository;
    private final NamedParameterJdbcTemplate template;
    private final ClientService clientService;
    private final EmployeeService employeeService;

    @Override
    public ChartResponseDTO findMemberAgeChart(Long clientNo) {
        String query = ChartQuery.MEMBER_AGE_CHART_QUERY;
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        log.info("[findMemberAgeChart] role: {}", role);
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            query = String.format(query, String.format("AND t.client_no = %s", userDetail.getPrimaryKey()));
        } else {
            if (clientNo != null) {
                query = String.format(query, String.format("AND t.client_no = %s", clientNo));
            } else {
                query = String.format(query, "");
            }
        }
        List<StatisticDTO> statisticDTOS = template.query(query, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};
        return ChartResponseDTO.builder().roles(roles).chartName("Member age chart").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
    }

    @Override
    public ChartResponseDTO findMemberLocationChart(Long clientNo) {
        String query = ChartQuery.MEMBER_LOCATION_CHART_QUERY;
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        log.info("[findMemberLocationChart] role: {}", role);
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            query = String.format(query, String.format("AND m.client_no = %s", userDetail.getPrimaryKey()));
        } else {
            if (clientNo != null) {
                query = String.format(query, String.format("AND m.client_no = %s", clientNo));
            } else {
                query = String.format(query, "");
            }
        }
        List<StatisticDTO> statisticDTOS = template.query(query, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};
        return ChartResponseDTO.builder().roles(roles).chartName("Member location chart").chartType(ChartConstant.BAR_CHART).statistics(statisticDTOS).build();
    }

    @Override
    public ChartResponseDTO findMemberGenderChart(Long clientNo) {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        log.info("[findMemberGenderChart] role: {}", role);
        List<Member> memberList;
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            memberList = memberRepository.findMemberByClientNoAndIsDeletedIsFalse(userDetail.getPrimaryKey());
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
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};
        return ChartResponseDTO.builder().chartName("Member gender chart").chartType(ChartConstant.BAR_CHART).statistics(statistics).roles(roles).build();
    }

    @Override
    public LineChartResponseDTO findMemberOnboardChart(Long[] clientNos) {
        List<SingleLineChartResponseDTO> list = new ArrayList<>();
        List<StatisticDTO> statisticDTOS;
        SingleLineChartResponseDTO singleLineChartResponseDTO;
        String query = ChartQuery.MEMBER_ONBOARD_CHART_QUERY;
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        log.info("[findMemberOnboardChart] role: {}", role);
        String tempQue;
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            tempQue = String.format(query, String.format("AND m.client_no = %s", userDetail.getPrimaryKey()));
            statisticDTOS = template.query(tempQue, new StatisticsRowMapper());
            singleLineChartResponseDTO = SingleLineChartResponseDTO.builder().chartName(clientService.getDetail(userDetail.getPrimaryKey()).getClientName()).statistics(statisticDTOS).build();
            list.add(singleLineChartResponseDTO);
        } else {
            if (clientNos != null) {
                for (Long clientNo : clientNos) {
                    tempQue = String.format(query, String.format("AND m.client_no = %s", clientNo));
                    statisticDTOS = template.query(tempQue, new StatisticsRowMapper());
                    singleLineChartResponseDTO = SingleLineChartResponseDTO.builder().chartName(clientService.getDetail(clientNo).getClientName()).statistics(statisticDTOS).build();
                    list.add(singleLineChartResponseDTO);
                }
            } else {
                tempQue = String.format(query, "");
                statisticDTOS = template.query(tempQue, new StatisticsRowMapper());
                singleLineChartResponseDTO = SingleLineChartResponseDTO.builder().chartName("All members").statistics(statisticDTOS).build();
                list.add(singleLineChartResponseDTO);
            }
        }
        return LineChartResponseDTO.builder().roles(roles).chartType(ChartConstant.LINE_CHART).lines(list).build();
    }

    @Override
    public ChartResponseDTO findClaimStatusChart(Long clientNo) {
        String query = ChartQuery.CLAIM_STATUS_CHART_QUERY;
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        log.info("[findClaimStatusChart] role: {}", role);
        String chartName;
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            query = String.format(query, String.format("AND m.client_no = %s", userDetail.getPrimaryKey()));
            chartName = String.format("Claim status statistics for client: %s", clientService.getDetail(userDetail.getPrimaryKey()).getClientName());
        } else if ("ROLE_BUSINESS_EMPLOYEE".equalsIgnoreCase(role)) {
            if (clientNo == null) {
                query = String.format(query, String.format("AND m.client_no IN(\n" +
                        "        SELECT ec.client_no\n" +
                        "        FROM HIAS.employee_client ec\n" +
                        "        WHERE ec.employee_no = %s\n" +
                        "    )", userDetail.getPrimaryKey()));
                chartName = String.format("Claims status statistics in charge by employee: %s", employeeService.findEmployeeByEmployeeNo(userDetail.getPrimaryKey()).getEmployeeName());
            } else {
                query = String.format(query, String.format("AND m.client_no IN(\n" +
                        "        SELECT ec.client_no\n" +
                        "        FROM HIAS.employee_client ec\n" +
                        "        WHERE ec.employee_no = %s AND ec.client_no = %s\n" +
                        "    )", userDetail.getPrimaryKey(), clientNo));
                chartName = String.format("Claims status statistics in charge by employee: %s with client: %s", employeeService.findEmployeeByEmployeeNo(userDetail.getPrimaryKey()).getEmployeeName(),
                        clientService.getDetail(clientNo).getClientName());
            }
        } else {
            if (clientNo != null) {
                query = String.format(query, String.format("AND m.client_no = %s", clientNo));
                chartName = String.format("Claim status statistics for to client: %s", clientService.getDetail(clientNo).getClientName());
            } else {
                query = String.format(query, "");
                chartName = "Claim status statistics";
            }
        }
        List<StatisticDTO> statisticDTOS = template.query(query, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};
        ChartResponseDTO chartResponseDTO = ChartResponseDTO.builder().roles(roles).chartName(chartName).chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();

        return chartResponseDTO;
    }

    @Override
    public ChartResponseDTO findPolicyByUsage(LocalDate startDate, LocalDate endDate) {
        String query = ChartQuery.POLICY_BY_USAGE;
        if (startDate == null) {
            query = String.format(query, "");
        } else {
            query = String.format(query, String.format("AND m.start_date BETWEEN '%s' :: timestamp AND '%s' :: timestamp", startDate, endDate));
        }
        List<StatisticDTO> statisticDTOS = template.query(query, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName()};
        ChartResponseDTO chartResponseDTO = ChartResponseDTO.builder().roles(roles).chartName("Policy usage").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
        return chartResponseDTO;
    }

    @Override
    public ChartResponseDTO findBusinessSectorChart() {
        List<StatisticDTO> statisticDTOS = template.query(ChartQuery.BUSINESS_SECTOR, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName()};
        ChartResponseDTO chartResponseDTO = ChartResponseDTO.builder().roles(roles).chartName("Business sectors").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
        return chartResponseDTO;
    }

    @Override
    public ChartResponseDTO findClaimBySpecialStatus(LocalDate startDate, LocalDate endDate) {
        String query = ChartQuery.APR_VIO_REJ_LEG;
        if (startDate == null) {
            query = String.format(query, "", "");
        } else {
            query = String.format(query, String.format("AND c.rejected_date BETWEEN '%s' AND '%s'", startDate, endDate),
                    String.format("AND c.approved_date BETWEEN '%s' AND '%s'", startDate, endDate));
        }
        List<StatisticDTO> statisticDTOS = template.query(query, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName()};
        ChartResponseDTO chartResponseDTO = ChartResponseDTO.builder().roles(roles).chartName("Claim with status: Approve, Legal Reject, Violation").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
        return chartResponseDTO;
    }

}
