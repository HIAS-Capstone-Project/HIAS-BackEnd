package com.hias.controller;

import com.hias.model.response.DistrictResponseDTO;
import com.hias.service.DistrictService;
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
@RequestMapping("api/district/")
@AllArgsConstructor
@Slf4j
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("find-by-province-no/{provinceNo}")
    public ResponseEntity<List<DistrictResponseDTO>> findByProvinceNo(@PathVariable Long provinceNo) {
        return new ResponseEntity<>(districtService.findByProvinceNo(provinceNo), HttpStatus.OK);
    }

    @GetMapping("find-by-district-no/{districtNo}")
    public ResponseEntity<DistrictResponseDTO> findByDistrictNo(@PathVariable Long districtNo) {
        return new ResponseEntity<>(districtService.findByDistrictNo(districtNo), HttpStatus.OK);
    }
}
