package com.hias;

import com.hias.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
public class HIASBackendApplication {

    @Autowired
    private EmailSenderService emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(HIASBackendApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerEmail() throws MessagingException {
        emailSenderService.sendEmailWithAttachment(
                "vietcuongtx@gmail.com",
                "This is body",
                "Subject of email",
                "\"C:\\Users\\ASUS\\Desktop\\For sending.txt\"");
    }
}
