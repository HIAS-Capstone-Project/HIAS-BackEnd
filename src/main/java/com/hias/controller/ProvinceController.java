package com.hias.controller;

import com.hias.model.response.ProvinceResponseDTO;
import com.hias.service.ProvinceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/province/")
@AllArgsConstructor
@Slf4j
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping("find-all")
    public ResponseEntity<List<ProvinceResponseDTO>> findAll() {
        return new ResponseEntity<>(provinceService.findAll(), HttpStatus.OK);
    }
}
