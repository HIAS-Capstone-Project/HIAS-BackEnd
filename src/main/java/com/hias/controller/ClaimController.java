package com.hias.controller;

import com.hias.constant.CommonConstant;
import com.hias.constant.FieldNameConstant;
import com.hias.constant.StatusCode;
import com.hias.exception.HIASException;
import com.hias.model.request.*;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.service.ClaimService;
import com.hias.service.ClaimServiceV2;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/claim/")
@AllArgsConstructor
@Slf4j
public class ClaimController {

    private ClaimService claimService;
    private ClaimServiceV2 claimServiceV2;

    @GetMapping("find-all")
    public ResponseEntity<List<ClaimResponseDTO>> findAll() {
        return new ResponseEntity<>(claimService.findAll(), HttpStatus.OK);
    }

    @GetMapping("view-detail/{claimNo}")
    public ResponseEntity<ClaimResponseDTO> viewDetail(@PathVariable Long claimNo) {
        return new ResponseEntity<>(claimService.viewDetail(claimNo), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<PagingResponseModel<ClaimResponseDTO>> search(
            @RequestParam(required = false) Long clientNo,
            @RequestParam(required = false) String statusCode,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                            direction = Sort.Direction.DESC)
            }) Pageable pageable) {

        return new ResponseEntity<>(claimService.search(searchValue, clientNo, StatusCode.findByString(statusCode), pageable), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody ClaimRequestDTO claimRequestDTO) {
        claimService.create(claimRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody ClaimUpdateRequestDTO claimUpdateRequestDTO) {
        claimService.update(claimUpdateRequestDTO);
        return new ResponseEntity<>(CommonConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("delete/{claimNo}")
    public ResponseEntity<String> deleteByClaimNo(@PathVariable Long claimNo) {
        claimService.deleteByClaimNo(claimNo);
        return new ResponseEntity<>(CommonConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("submit")
    public ResponseEntity<String> submit(@RequestBody ClaimSubmitRequestDTO claimSubmitRequestDTO) {

        claimServiceV2.submit(claimSubmitRequestDTO);
        return new ResponseEntity<>(CommonConstant.SUBMIT_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("save-draft")
    public ResponseEntity<String> saveDraft(@RequestBody ClaimSubmitRequestDTO claimSubmitRequestDTO) {

        claimServiceV2.saveDraft(claimSubmitRequestDTO);
        return new ResponseEntity<>(CommonConstant.SAVED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("cancel-claim/{claimNo}")
    public ResponseEntity<String> cancelClaim(@PathVariable Long claimNo) throws HIASException {
        claimService.cancelClaim(claimNo);
        return new ResponseEntity<>(CommonConstant.CANCELED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("business-verified/{claimNo}")
    public ResponseEntity<String> businessVerified(@PathVariable Long claimNo) {
        claimService.businessVerified(claimNo);
        return new ResponseEntity<>(CommonConstant.APPROVE_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("medical-verified/{claimNo}")
    public ResponseEntity<String> medicalVerified(@PathVariable Long claimNo) {
        claimService.medicalVerified(claimNo);
        return new ResponseEntity<>(CommonConstant.APPROVE_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("start-progress/{claimNo}")
    public ResponseEntity<String> startProgress(@PathVariable Long claimNo) {
        claimService.startProgress(claimNo);
        return new ResponseEntity<>(CommonConstant.START_PROGRESS_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("approve/{claimNo}")
    public ResponseEntity<String> approve(@PathVariable Long claimNo) {
        claimService.approve(claimNo);
        return new ResponseEntity<>(CommonConstant.APPROVE_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("settle-claim")
    public ResponseEntity<String> settleClaim(@RequestBody ClaimPaymentRequestDTO claimPaymentRequestDTO) {
        claimService.settleClaim(claimPaymentRequestDTO);
        return new ResponseEntity<>(CommonConstant.SETTLE_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("reject-claim")
    public ResponseEntity<String> rejectClaim(@RequestBody ClaimRejectRequestDTO claimRejectRequestDTO) {
        claimService.rejectClaim(claimRejectRequestDTO);
        return new ResponseEntity<>(CommonConstant.REJECT_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("return-claim")
    public ResponseEntity<String> returnClaim(@RequestBody ClaimReturnRequestDTO claimReturnRequestDTO) {
        claimService.returnClaim(claimReturnRequestDTO);
        return new ResponseEntity<>(CommonConstant.RETURNED_SUCCESSFULLY, HttpStatus.OK);
    }
}
