package com.hias.controller;


import com.hias.model.response.BankResponseDTO;
import com.hias.service.BankService;
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

    private final BankService bankService;

    @GetMapping("find-all")
    public ResponseEntity<List<BankResponseDTO>> findAll() {
        return new ResponseEntity<>(bankService.findAll(), HttpStatus.OK);
    }
}
