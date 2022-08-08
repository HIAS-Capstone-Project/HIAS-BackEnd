package com.hias.controller;


import com.hias.constant.CommonConstant;
import com.hias.constant.FieldNameConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.BenefitItemRequestDTO;
import com.hias.model.response.BenefitItemResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.service.BenefitItemService;
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
@RequestMapping("api/benefit-item/")
@AllArgsConstructor
@Slf4j
public class BenefitItemController {

    private final BenefitItemService benefitItemService;

    @GetMapping("find-by-benefit-item-no/{benefitItemNo}")
    public ResponseEntity<BenefitItemResponseDTO> findByBenefitItemNo(@PathVariable Long benefitItemNo) {
        return new ResponseEntity<>(benefitItemService.findByBenefitItemNo(benefitItemNo), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<PagingResponseModel<BenefitItemResponseDTO>> search(@RequestParam(required = false) String searchValue,
                                                                              @PageableDefault(page = 0, size = 10)
                                                                              @SortDefault.SortDefaults({
                                                                                      @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                                                                                              direction = Sort.Direction.DESC)
                                                                              }) Pageable pageable) {
        return new ResponseEntity<>(benefitItemService.search(searchValue, pageable), HttpStatus.OK);
    }

    @GetMapping("find-all")
    public ResponseEntity<List<BenefitItemResponseDTO>> findAll() {
        return new ResponseEntity<>(benefitItemService.findAll(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody BenefitItemRequestDTO benefitItemRequestDTO) throws HIASException {
        benefitItemService.create(benefitItemRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody BenefitItemRequestDTO benefitItemRequestDTO) {
        benefitItemService.update(benefitItemRequestDTO);
        return new ResponseEntity<>(CommonConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("delete/{benefitItemNo}")
    public ResponseEntity<String> delete(@PathVariable Long benefitItemNo) throws HIASException {
        benefitItemService.deleteByBenefitItemNo(benefitItemNo);
        return new ResponseEntity<>(CommonConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

}
