package com.hias.controller;


import com.hias.constant.CommonConstant;
import com.hias.constant.FieldNameConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/member/")
@AllArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("find-all")
    public ResponseEntity<List<MemberResponseDTO>> getAll() {
        return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity findMember(@RequestParam(required = false) String key, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false, defaultValue = "memberName,asc") String[] sort) {
        return new ResponseEntity<>(memberService.findMember(key, pageIndex, pageSize, sort), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<PagingResponseModel<MemberResponseDTO>> search(@RequestParam(required = false) String searchValue,
                                                                         @PageableDefault(page = 0, size = 10)
                                                                         @SortDefault.SortDefaults({
                                                                                 @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                                                                                         direction = Sort.Direction.DESC)
                                                                         }) Pageable pageable) {
        return new ResponseEntity<>(memberService.search(searchValue, pageable), HttpStatus.OK);
    }

    @GetMapping("search-by-health-card-no")
    public ResponseEntity<MemberResponseDTO> searchByHealthCardNo(@RequestParam String healthCardNo,
                                                                  @RequestParam
                                                                  @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate visitDate) throws HIASException {

        return new ResponseEntity<>(memberService.searchByHealthCardNo(healthCardNo, visitDate), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> findMember(@RequestParam Long memberNo) throws Exception {
        memberService.deleteMemberByMemberNo(memberNo);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("find-detail")
    public ResponseEntity<MemberResponseDTO> findByMemberNo(@RequestParam Long memberNo) {
        return new ResponseEntity<>(memberService.findByMemberNo(memberNo), HttpStatus.OK);
    }

    @PostMapping("save")
    public ResponseEntity<String> saveMember(@RequestBody MemberRequestDTO memberRequestDTO) throws HIASException {
        log.info(memberRequestDTO.toString());
        memberService.saveMember(memberRequestDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody MemberRequestDTO memberRequestDTO) throws HIASException {
        memberService.createMember(memberRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody MemberRequestDTO memberRequestDTO) throws HIASException {
        memberService.updateMember(memberRequestDTO);
        return new ResponseEntity<>(CommonConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }
}
