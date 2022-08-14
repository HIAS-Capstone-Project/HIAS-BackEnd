package com.hias.controller;

import com.hias.model.response.ChartResponseDTO;
import com.hias.service.ChartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/chart/")
@AllArgsConstructor
@Slf4j
public class ChartController {
    private ChartService chartService;

    // just for admin, accountant, employee, client. client only sees their employees, others can see all and filter by client
    @GetMapping("member-by-age")
    public ResponseEntity<ChartResponseDTO> findMemberByAge(@RequestParam(required = false) Long clientNo) {
        ChartResponseDTO chartResponseDTO = chartService.findMemberAgeChart(clientNo);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    // just for admin, accountant, employee, client. client only sees their employees, others can see all and filter by client
    @GetMapping("member-by-gender")
    public ResponseEntity<ChartResponseDTO> findMemberByGender(@RequestParam(required = false) Long clientNo) {
        ChartResponseDTO chartResponseDTO = chartService.findMemberGenderChart(clientNo);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    // just for admin, accountant, employee, client. client only sees their employees, others can see all and filter by client
    @GetMapping("member-by-location")
    public ResponseEntity<ChartResponseDTO> findMemberByLocation(@RequestParam(required = false) Long clientNo) {
        ChartResponseDTO chartResponseDTO = chartService.findMemberLocationChart(clientNo);
        return new ResponseEntity<>(chartResponseDTO, HttpStatus.OK);
    }

    // just for admin, accountant, employee, client. client only sees their employees, others can see all and filter by client
    @GetMapping("member-by-onboard-year")
    public ResponseEntity<List<ChartResponseDTO>> findMemberByOnboardYear(@RequestParam(required = false) Long[] clientNos) {
        List<ChartResponseDTO> chartResponseDTOs = chartService.findMemberOnboardChart(clientNos);
        return new ResponseEntity<>(chartResponseDTOs, HttpStatus.OK);
    }
}
