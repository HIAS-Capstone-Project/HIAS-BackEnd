package com.hias.controller;

import com.hias.model.response.BusinessSectorResponseDTO;
import com.hias.service.BusinessSectorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/business-sector/")
@AllArgsConstructor
@Slf4j
public class BusinessSectorController {
    private final BusinessSectorService businessSectorService;

    @GetMapping("find-all")
    public ResponseEntity<List<BusinessSectorResponseDTO>> findAll() {
        return new ResponseEntity<>(businessSectorService.findAll(), HttpStatus.OK);
    }
}
