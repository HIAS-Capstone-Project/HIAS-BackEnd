package com.hias;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class HIASBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HIASBackendApplication.class, args);
    }

    @PostConstruct
    void configTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("VST"));
        log.info("now : " + LocalDateTime.now());
    }
}
