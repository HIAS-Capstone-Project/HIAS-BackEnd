package com.hias.configuration;

import com.hias.constant.HttpsConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(HttpsConstant.GET, HttpsConstant.POST, HttpsConstant.PUT, HttpsConstant.DELETE, HttpsConstant.PATCH)
                .allowedOrigins("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
