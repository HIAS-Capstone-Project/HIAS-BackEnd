package com.hias.controller;


import com.hias.constant.CommonConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.BenefitRequestDTO;
import com.hias.model.response.BenefitResponseDTO;
import com.hias.service.BenefitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/benefit/")
@AllArgsConstructor
@Slf4j
public class BenefitController {

    private final BenefitService benefitService;

    @GetMapping("find-by-benefit-no/{benefitNo}")
    public ResponseEntity<BenefitResponseDTO> findByBenefitNo(@PathVariable Long benefitNo) {
        return new ResponseEntity<>(benefitService.findByBenefitNo(benefitNo), HttpStatus.OK);
    }

    @GetMapping("find-all")
    public ResponseEntity<List<BenefitResponseDTO>> findAll() {
        return new ResponseEntity<>(benefitService.findAll(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody BenefitRequestDTO benefitRequestDTO) throws HIASException {
        benefitService.create(benefitRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @DeleteMapping("delete-by-benefit-no/{benefitNo}")
    public ResponseEntity<String> delete(@PathVariable Long benefitNo) {
        benefitService.deleteByBenefitNo(benefitNo);
        return new ResponseEntity<>(CommonConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody BenefitRequestDTO benefitRequestDTO) {
        benefitService.update(benefitRequestDTO);
        return new ResponseEntity<>(CommonConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }
}
