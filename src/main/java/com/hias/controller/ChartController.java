package com.hias.controller;

import com.hias.model.response.ChartResponseDTO;
import com.hias.model.response.LineChartResponseDTO;
import com.hias.service.ChartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("api/chart/")
@AllArgsConstructor
@Slf4j
public class ChartController {
    private ChartService chartService;

    /* just for admin, accountant, employee, client. c
    client only sees their members,
    others can see all and filter by client
    */
    @GetMapping("member-by-age")
    public ResponseEntity<ChartResponseDTO> findMemberByAge(@RequestParam(required = false) Long clientNo) {
        ChartResponseDTO chartResponseDTO = chartService.findMemberAgeChart(clientNo);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    /* just for admin, accountant, employee, client. c
    client only sees their members,
    others can see all and filter by client
    */
    @GetMapping("member-by-gender")
    public ResponseEntity<ChartResponseDTO> findMemberByGender(@RequestParam(required = false) Long clientNo) {
        ChartResponseDTO chartResponseDTO = chartService.findMemberGenderChart(clientNo);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    /* just for admin, accountant, employee, client. c
    client only sees their members,
    others can see all and filter by client
    */
    @GetMapping("member-by-location")
    public ResponseEntity<ChartResponseDTO> findMemberByLocation(@RequestParam(required = false) Long clientNo) {
        ChartResponseDTO chartResponseDTO = chartService.findMemberLocationChart(clientNo);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    /* just for admin, accountant, employee, client.
    client only sees their members,
    others can see all and filter by client
    */
    @GetMapping("member-by-onboard-year")
    public ResponseEntity<LineChartResponseDTO> findMemberByOnboardYear(@RequestParam(required = false) Long[] clientNos) {
        LineChartResponseDTO chartResponseDTOs = chartService.findMemberOnboardChart(clientNos);
        return new ResponseEntity<>(chartResponseDTOs, HttpStatus.OK);
    }

    /*
    just for admin, accountant, employee, client.
    client only sees claims belonging their members,
    employees only see claims belonging to member belonging to client that they are in charge of
    others can see all and filter by client
    */
    @GetMapping("claim-by-all-status")
    public ResponseEntity<ChartResponseDTO> findClaimByStatus(@RequestParam(required = false) Long clientNo) {
        ChartResponseDTO chartResponseDTO = chartService.findClaimStatusChart(clientNo);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    /*
    just for admin, accountant, employee
    others can see all and filter by time
    localDate format: yyyy-MM-dd
    */
    @GetMapping("policy-by-usage")
    public ResponseEntity<ChartResponseDTO> findPolicyByUsage(@RequestParam(required = false)
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                              @RequestParam(required = false)
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ChartResponseDTO chartResponseDTO = chartService.findPolicyByUsage(startDate, endDate);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    /*
    just for admin, accountant, employee.
    others can see all and filter by client
    */
    @GetMapping("business-sector")
    public ResponseEntity<ChartResponseDTO> findBusinessSectorChart() {
        ChartResponseDTO chartResponseDTO = chartService.findBusinessSectorChart();
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    /*
    just for admin, accountant, employee
    others can see all and filter by time
    localDate format: yyyy-MM-dd
    */
    @GetMapping("claim-by-special-status")
    public ResponseEntity<ChartResponseDTO> findClaimByStatus(@RequestParam(required = false)
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                              @RequestParam(required = false)
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ChartResponseDTO chartResponseDTO = chartService.findClaimBySpecialStatus(startDate, endDate);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }
}
