package com.hias.controller;

import com.hias.model.response.ClaimResponseDTO;
import com.hias.service.ClaimService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/claim/")
@AllArgsConstructor
@Slf4j
public class ClaimController {

    private ClaimService claimService;

    @GetMapping("find-all")
    public ResponseEntity<List<ClaimResponseDTO>> findAll() {
        return new ResponseEntity<>(claimService.findAll(), HttpStatus.OK);
    }
}
