package com.hias.controller;

import com.hias.constant.FieldNameConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.PolicyRequestDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.model.response.PolicyResponseDTO;
import com.hias.service.PolicyService;
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
@RequestMapping("api/policy/")
@AllArgsConstructor
@Slf4j
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("get-all")
    public ResponseEntity<List<PolicyResponseDTO>> getAll() {
        return new ResponseEntity<>(policyService.getAll(), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<PagingResponseModel<PolicyResponseDTO>> search(@RequestParam(required = false) String searchValue,
                                                                         @PageableDefault(page = 0, size = 10)
                                                                         @SortDefault.SortDefaults({
                                                                                 @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                                                                                         direction = Sort.Direction.DESC)
                                                                         }) Pageable pageable) {
        return new ResponseEntity<>(policyService.search(searchValue, pageable), HttpStatus.OK);
    }

    @GetMapping("search-for-client")
    public ResponseEntity<PagingResponseModel<PolicyResponseDTO>> searchForClient(
            @RequestParam Long clientNo,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                            direction = Sort.Direction.DESC)
            }) Pageable pageable) {
        return new ResponseEntity<>(policyService.searchForClient(clientNo, searchValue, pageable), HttpStatus.OK);
    }

    @GetMapping("policy-detail")
    public ResponseEntity<PolicyResponseDTO> getDetail(@RequestParam Long policyNo) {
        return new ResponseEntity<>(policyService.getDetail(policyNo), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<PolicyResponseDTO> create(@RequestBody PolicyRequestDTO policyRequestDTO) throws HIASException {
        return new ResponseEntity<>(policyService.create(policyRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<PolicyResponseDTO> update(@RequestBody PolicyRequestDTO policyRequestDTODTO) {
        return new ResponseEntity<>(policyService.update(policyRequestDTODTO), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<PolicyResponseDTO> delete(@RequestParam Long policyNo) {
        return new ResponseEntity<>(policyService.delete(policyNo), HttpStatus.OK);
    }
}
