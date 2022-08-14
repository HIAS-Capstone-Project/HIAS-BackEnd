package com.hias.controller;

import com.hias.constant.CommonConstant;
import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.service.ClaimService;
import com.hias.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody ClaimRequestDTO claimRequestDTO) {
        claimService.create(claimRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody ClaimRequestDTO claimRequestDTO) {
        claimService.create(claimRequestDTO);
        return new ResponseEntity<>(CommonConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("delete-by-claim-no/{claimNo}")
    public ResponseEntity<String> deleteByClaimNo(@PathVariable Long claimNo) {
        claimService.deleteByClaimNo(claimNo);
        return new ResponseEntity<>(CommonConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping(path = "submit-by-member")
    public ResponseEntity<String> submitByMember(@RequestPart ClaimSubmitRequestDTO claimSubmitRequestDTO,
                                                 @RequestPart List<MultipartFile> documents) {
        if (documents != null) {
            System.out.println(documents.get(0).getOriginalFilename() + " " + documents.get(0).getName());
        }
        return new ResponseEntity<>(CommonConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("save-draft-for-member")
    public ResponseEntity<String> saveDraftForMember(@RequestPart ClaimSubmitRequestDTO claimSubmitRequestDTO,
                                                     @RequestPart List<MultipartFile> documents) throws IOException {

        claimService.saveDraftForMember(claimSubmitRequestDTO, documents);
        return new ResponseEntity<>(CommonConstant.SAVED_SUCCESSFULLY, HttpStatus.OK);
    }
}
