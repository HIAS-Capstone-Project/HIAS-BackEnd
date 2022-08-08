package com.hias.controller;


import com.hias.model.response.DepartmentResponseDTO;
import com.hias.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bank/")
@AllArgsConstructor
@Slf4j
public class BankController {

    private final DepartmentService departmentService;

    @GetMapping("find-all")
    public ResponseEntity<List<DepartmentResponseDTO>> findAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }
}
