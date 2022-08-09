package com.hias.controller;


import com.hias.model.response.LiscenseResponseDTO;
import com.hias.service.LiscenseService;
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
@RequestMapping("api/liscense/")
@AllArgsConstructor
@Slf4j
public class LiscenseController {

    private final LiscenseService liscenseService;

    @GetMapping("find-all")
    public ResponseEntity<List<LiscenseResponseDTO>> findAll() {
        return new ResponseEntity<>(liscenseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("find-by-benefit-no/{benefitNo}")
    public ResponseEntity<List<LiscenseResponseDTO>> findByBenefitNo(@PathVariable Long benefitNo) {
        return new ResponseEntity<>(liscenseService.findAllByBenefitNo(benefitNo), HttpStatus.OK);
    }
}
