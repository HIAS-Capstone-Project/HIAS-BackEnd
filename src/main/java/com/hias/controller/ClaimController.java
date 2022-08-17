package com.hias.controller;

import com.hias.constant.CommonConstant;
import com.hias.constant.FieldNameConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.service.ClaimService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

    @GetMapping("view-detail/{claimNo}")
    public ResponseEntity<ClaimResponseDTO> viewDetail(@PathVariable Long claimNo) {
        return new ResponseEntity<>(claimService.viewDetail(claimNo), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<PagingResponseModel<ClaimResponseDTO>> search(@RequestParam(required = false) String searchValue,
                                                                        @PageableDefault(page = 0, size = 10)
                                                                        @SortDefault.SortDefaults({
                                                                                @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                                                                                        direction = Sort.Direction.DESC)
                                                                        }) Pageable pageable) {
        return new ResponseEntity<>(claimService.search(searchValue, pageable), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody ClaimRequestDTO claimRequestDTO) {
        claimService.create(claimRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody ClaimRequestDTO claimRequestDTO) {
        claimService.update(claimRequestDTO);
        return new ResponseEntity<>(CommonConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("delete/{claimNo}")
    public ResponseEntity<String> deleteByClaimNo(@PathVariable Long claimNo) {
        claimService.deleteByClaimNo(claimNo);
        return new ResponseEntity<>(CommonConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("submit-by-member")
    public ResponseEntity<String> submitByMember(@RequestPart ClaimSubmitRequestDTO claimSubmitRequestDTO,
                                                 @RequestPart List<MultipartFile> documents) throws IOException, HIASException {

        claimService.submitForMember(claimSubmitRequestDTO, documents);
        return new ResponseEntity<>(CommonConstant.SUBMIT_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("save-draft-for-member")
    public ResponseEntity<String> saveDraftForMember(@RequestPart ClaimSubmitRequestDTO claimSubmitRequestDTO,
                                                     @RequestPart List<MultipartFile> documents) throws IOException, HIASException {

        claimService.saveDraftForMember(claimSubmitRequestDTO, documents);
        return new ResponseEntity<>(CommonConstant.SAVED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("cancel-claim/{claimNo}")
    public ResponseEntity<String> cancelClaim(@PathVariable Long claimNo) throws HIASException {
        claimService.cancelClaim(claimNo);
        return new ResponseEntity<>(CommonConstant.CANCELED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("business-approval/{claimNo}")
    public ResponseEntity<String> businessApproval(@PathVariable Long claimNo) throws HIASException {
        claimService.businessApproval(claimNo);
        return new ResponseEntity<>(CommonConstant.APPROVAL_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("start-progress/{claimNo}")
    public ResponseEntity<String> startProgress(@PathVariable Long claimNo) throws HIASException {
        claimService.startProgress(claimNo);
        return new ResponseEntity<>(CommonConstant.START_PROGRESS_SUCCESSFULLY, HttpStatus.OK);
    }
}
