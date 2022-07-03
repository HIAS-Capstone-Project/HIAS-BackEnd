package com.hias.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ResourceBundleConfig {

    private static final String CLASSPATH_MESSAGE = "classpath:message";
    private static final String UTF_8 = "UTF-8";

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasenames(CLASSPATH_MESSAGE);
        messageBundle.setDefaultEncoding(UTF_8);
        return messageBundle;
    }
}
