package com.hias.controller;

import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PolicyResponseDTO;
import com.hias.service.PolicyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/policy/")
@AllArgsConstructor
@Slf4j
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("get-all")
    public ResponseEntity<List<PolicyResponseDTO>> getAll() {
        return new ResponseEntity<>(policyService.getAll(), HttpStatus.OK);
    }

    @GetMapping("policy-detail")
    public ResponseEntity<PolicyResponseDTO> getDetail(@RequestParam Long policyNo) {
        return new ResponseEntity<>(policyService.getDetail(policyNo), HttpStatus.OK);
    }

    @PostMapping("create-policy")
    public ResponseEntity<PolicyResponseDTO> createPolicy(@RequestBody PolicyRequestDTO policyRequestDTO) {
        return new ResponseEntity<>(policyService.createPolicy(policyRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("update-policy")
    public ResponseEntity<PolicyResponseDTO> updatePolicy(@RequestBody PolicyRequestDTO policyRequestDTODTO) {
        return new ResponseEntity<>(policyService.updatePolicy(policyRequestDTODTO), HttpStatus.OK);
    }

    @DeleteMapping("detele-policy")
    public ResponseEntity<PolicyResponseDTO> deletePolicy(@RequestParam Long policyNo) {
        return new ResponseEntity<>(policyService.deletePolicy(policyNo), HttpStatus.OK);
    }
}
