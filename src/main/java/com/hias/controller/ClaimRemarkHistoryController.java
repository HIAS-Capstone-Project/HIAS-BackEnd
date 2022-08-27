package com.hias.controller;

import com.hias.model.response.ClaimRemarkHistoryResponseDTO;
import com.hias.service.ClaimRemarkHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/claim-remark-history/")
@AllArgsConstructor
@Slf4j
public class ClaimRemarkHistoryController {

    private ClaimRemarkHistoryService claimRemarkHistoryService;

    @GetMapping("find-by-claim-no/{claimNo}")
    public ResponseEntity<List<ClaimRemarkHistoryResponseDTO>> findByClaimNo(Long claimNo) {
        return new ResponseEntity<>(claimRemarkHistoryService.findByClaimNo(claimNo), HttpStatus.OK);
    }
}
