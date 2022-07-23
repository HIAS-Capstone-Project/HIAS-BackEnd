package com.hias.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

@Configuration
@Slf4j
public class CustomConfiguration {

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Bucket getBucket() {
        ClassPathResource serviceAccount = new ClassPathResource("firebase/firebase.json");
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket("hias-fire-base.appspot.com")
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        Bucket bucket = StorageClient.getInstance(FirebaseApp.initializeApp(options)).bucket();
        log.info("Init Bucket : {}", bucket);
        return bucket;
    }
}
