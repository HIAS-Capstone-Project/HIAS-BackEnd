package com.hias.controller;


import com.hias.model.response.LicenseResponseDTO;
import com.hias.service.LicenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/license/")
@AllArgsConstructor
@Slf4j
public class LicenseController {

    private final LicenseService licenseService;

    @GetMapping("find-all")
    public ResponseEntity<List<LicenseResponseDTO>> findAll() {
        return new ResponseEntity<>(licenseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("find-by-benefit-no/{benefitNo}")
    public ResponseEntity<List<LicenseResponseDTO>> findByBenefitNo(@PathVariable Long benefitNo) {
        return new ResponseEntity<>(licenseService.findAllByBenefitNo(benefitNo), HttpStatus.OK);
    }
}
