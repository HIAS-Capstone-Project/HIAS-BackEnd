package com.hias.controller;

import com.hias.model.response.EmploymentTypeResponseDTO;
import com.hias.service.EmploymentTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/employment-type/")
@AllArgsConstructor
@Slf4j
public class EmploymentTypeController {
    private final EmploymentTypeService employmentTypeService;

    @GetMapping("find-all")
    public ResponseEntity<List<EmploymentTypeResponseDTO>> findAll() {
        return new ResponseEntity<>(employmentTypeService.findAll(), HttpStatus.OK);
    }

}
