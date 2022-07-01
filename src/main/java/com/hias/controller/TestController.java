package com.hias.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test/")
@AllArgsConstructor
@Slf4j
public class TestController {


    @PostMapping("ko")
    public ResponseEntity<String> login() {
        log.info("OK lam");
        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
    }

}
