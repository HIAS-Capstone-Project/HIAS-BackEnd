package com.hias.controller;


import com.hias.constant.CommonConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.MemberRequestDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/member/")
@AllArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("list")
    public ResponseEntity findMember(@RequestParam(required = false) String key, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false, defaultValue = "memberName,asc") String[] sort) {
        return new ResponseEntity<>(memberService.findMember(key, pageIndex, pageSize, sort), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> findMember(@RequestParam Long memberNo) throws Exception {
        memberService.deleteMemberByMemberNo(memberNo);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("find-detail")
    public ResponseEntity<MemberResponseDTO> findMemberById(@RequestParam Long memberNo) {
        return new ResponseEntity<>(memberService.findMemberByMemberNo(memberNo), HttpStatus.OK);
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
        memberService.createMember(memberRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }
}
