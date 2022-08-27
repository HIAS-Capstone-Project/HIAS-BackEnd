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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return ChartResponseDTO.builder().roles(roles).chartName("Biểu đồ độ tuổi người tham gia bảo hiểm").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
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
        return ChartResponseDTO.builder().roles(roles).chartName("Biểu đồ nơi cư trú của người tham gia bảo hiểm").chartType(ChartConstant.BAR_CHART).statistics(statisticDTOS).build();
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
        statistics.add(new StatisticDTO("Nam", memberList.stream().filter(o -> "M".equalsIgnoreCase(o.getGenderEnum().getCode())).count()));
        statistics.add(new StatisticDTO("Nữ", memberList.stream().filter(o -> "F".equalsIgnoreCase(o.getGenderEnum().getCode())).count()));
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};
        return ChartResponseDTO.builder().chartName("Biểu đồ giới tính của người tham gia bảo hiểm").chartType(ChartConstant.BAR_CHART).statistics(statistics).roles(roles).build();
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
            singleLineChartResponseDTO = SingleLineChartResponseDTO.builder().chartName("Đồ thị lượng số lượng người tham gia bảo hiểm theo từng năm của công ty" + clientService.getDetail(userDetail.getPrimaryKey()).getClientName()).statistics(statisticDTOS).build();
            list.add(singleLineChartResponseDTO);
        } else {
            if (clientNos != null) {
                for (Long clientNo : clientNos) {
                    tempQue = String.format(query, String.format("AND m.client_no = %s", clientNo));
                    statisticDTOS = template.query(tempQue, new StatisticsRowMapper());
                    singleLineChartResponseDTO = SingleLineChartResponseDTO.builder().chartName("Đồ thị lượng số lượng người tham gia bảo hiểm theo từng năm của công ty" + clientService.getDetail(clientNo).getClientName()).statistics(statisticDTOS).build();
                    list.add(singleLineChartResponseDTO);
                }
            } else {
                tempQue = String.format(query, "");
                statisticDTOS = template.query(tempQue, new StatisticsRowMapper());
                singleLineChartResponseDTO = SingleLineChartResponseDTO.builder().chartName("Đồ thị lượng số lượng người tham gia bảo hiểm theo từng năm").statistics(statisticDTOS).build();
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
        statisticDTOS = statisticDTOS.stream().map(o -> {
            switch (o.getKey()) {
                case "DRA":
                    o.setKey("Bản nháp");
                    return o;
                case "CXL":
                    o.setKey("Tự hủy");
                    return o;
                case "SUB":
                    o.setKey("Đã nộp");
                    return o;
                case "BV":
                    o.setKey("Đã qua thẩm định nghiệp vụ");
                    return o;
                case "BVY":
                    o.setKey("Đang thẩm định nghiệp vụ");
                    return o;
                case "MV":
                    o.setKey("Đã thẩm định y tế");
                    return o;
                case "MVY":
                    o.setKey("Đang thẩm định y tế");
                    return o;
                case "WFA":
                    o.setKey("Đang chờ chấp thuận");
                    return o;
                case "APR":
                    o.setKey("Đã chấp thuận");
                    return o;
                case "PAY":
                    o.setKey("Đang xử lý thanh toán");
                    return o;
                case "SET":
                    o.setKey("Hoàn thành");
                    return o;
                case "RET":
                    o.setKey("Bị trả lại");
                    return o;
                case "REJ":
                    o.setKey("Từ chối");
                    return o;
                default:
                    return o;
            }
        }).collect(Collectors.toList());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};

        return ChartResponseDTO.builder().roles(roles).chartName(chartName).chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
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
        return ChartResponseDTO.builder().roles(roles).chartName("Thống kê những chính sách được sử dụng nhiều").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
    }

    @Override
    public ChartResponseDTO findBusinessSectorChart() {
        List<StatisticDTO> statisticDTOS = template.query(ChartQuery.BUSINESS_SECTOR, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName()};
        return ChartResponseDTO.builder().roles(roles).chartName("Thống kê lĩnh vực kinh doanh").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
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
        return ChartResponseDTO.builder().roles(roles).chartName("Claim với trạng thái: Hoàn tất, Thiếu giấy tờ, Vi phạm").chartType(ChartConstant.PIE_CHART).statistics(statisticDTOS).build();
    }

    @Override
    public List<StatisticDTO> findAll() {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        String query;
        Long id = userDetail.getPrimaryKey();
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            log.info("{findAll} ROLE_CLIENT");
            query = String.format(ChartQuery.FIND_ALL_STATISTICS_ROLE_CLIENT, id, id, id, id);
        } else if ("ROLE_BUSINESS_EMPLOYEE".equalsIgnoreCase(role)) {
            log.info("{findAll} ROLE_BUSINESS_EMPLOYEE");
            query = String.format(ChartQuery.FIND_ALL_STATISTICS_ROLE_EMP, id, id, id, id);
        } else {
            query = ChartQuery.FIND_ALL_STATISTICS;
        }
        List<StatisticDTO> statisticDTOS = template.query(query, new StatisticsRowMapper());
        Collections.sort(statisticDTOS);
        return statisticDTOS;
    }

    @Override
    public ChartResponseDTO findPayment(Long year, String timeFilterBy, Long clientNo) {
        String query = ChartQuery.
                PAYMENT_CHART_QUERY;
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetail.getRoles().get(0);
        log.info("[findPayment] role: {}", role);
        String chartName, roleCondition, selectedField, lastCondition;
        if ("ROLE_CLIENT".equalsIgnoreCase(role)) {
            roleCondition = String.format("AND m.client_no = %s", userDetail.getPrimaryKey());
            chartName = String.format("Tổng bồi hoàn cho công ty: %s", clientService.getDetail(userDetail.getPrimaryKey()).getClientName());
        } else if ("ROLE_BUSINESS_EMPLOYEE".equalsIgnoreCase(role)) {
            if (clientNo == null) {
                roleCondition = String.format("AND m.client_no IN(\n" +
                        "        SELECT ec.client_no\n" +
                        "        FROM HIAS.employee_client ec\n" +
                        "        WHERE ec.employee_no = %s\n" +
                        "    )", userDetail.getPrimaryKey());
                chartName = String.format("Tổng bồi hoàn cho người tham tham gia bảo hiểm phụ trach bỏi nhân viên: %s", employeeService.findEmployeeByEmployeeNo(userDetail.getPrimaryKey()).getEmployeeName());
            } else {
                roleCondition = String.format("AND m.client_no IN(\n" +
                        "        SELECT ec.client_no\n" +
                        "        FROM HIAS.employee_client ec\n" +
                        "        WHERE ec.employee_no = %s AND ec.client_no = %s\n" +
                        "    )", userDetail.getPrimaryKey(), clientNo);
                chartName = String.format("Tổng bồi hoàn cho người tham tham gia bảo hiểm phụ trach bỏi nhân viên: %s  công ty: %s", employeeService.findEmployeeByEmployeeNo(userDetail.getPrimaryKey()).getEmployeeName(),
                        clientService.getDetail(clientNo).getClientName());
            }
        } else {
            if (clientNo != null) {
                roleCondition = String.format("AND m.client_no = %s", clientNo);
                chartName = String.format("Tổng bồi hoàn cho công ty: %s", clientService.getDetail(clientNo).getClientName());
            } else {
                roleCondition = "";
                chartName = "Số liệu tiền bồi hoàn bảo hiểm";
            }
        }
        if (year == null) {
            log.info("Filter by year");
            selectedField = "yyyy";
            lastCondition = "GROUP BY to_char(c.payment_date,'yyyy')";
        } else {
            selectedField = "MM";
            lastCondition = String.format("AND DATE_PART('year', c.payment_date) = %s\n" +
                    "GROUP BY to_char(c.payment_date,'MM')", year);
        }
        query = String.format(query, selectedField, roleCondition, lastCondition);
        List<StatisticDTO> statisticDTOS = template.query(query, new StatisticsRowMapper());
        String[] roles = {RoleEnum.ROLE_SYSTEM_ADMIN.getName(), RoleEnum.ROLE_ACCOUNTANT.getName(), RoleEnum.ROLE_BUSINESS_EMPLOYEE.getName(), RoleEnum.ROLE_CLIENT.getName()};
        if (year != null && "quarter".equalsIgnoreCase(timeFilterBy)) {
            log.info("Filter by quarter in year {}", year);
            List<StatisticDTO> newStatisticDTOList = new ArrayList<>();
            long tempValue;
            for (int i = 1; i <= 4; i++) {
                tempValue = 0;
                for (StatisticDTO statisticDTO : statisticDTOS) {
                    if (Integer.parseInt(statisticDTO.getKey()) > 3 * (i - 1) && Integer.parseInt(statisticDTO.getKey()) <= 3 * i) {
                        tempValue += statisticDTO.getValue();
                    }
                }
                newStatisticDTOList.add(StatisticDTO.builder().key(String.format("Qúy %s", i)).value(tempValue).build());
            }
            return ChartResponseDTO.builder().chartName(chartName).chartType(ChartConstant.BAR_CHART).roles(roles).statistics(newStatisticDTOList).build();
        }
        return ChartResponseDTO.builder().chartName(chartName).chartType(ChartConstant.BAR_CHART).roles(roles).statistics(statisticDTOS).build();

    }

}
