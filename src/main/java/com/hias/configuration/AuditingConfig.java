package com.hias.configuration;

import com.hias.constant.SecurityConstant;
import com.hias.security.dto.UserDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}

class SpringSecurityAuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of(SecurityConstant.SYSTEM);
        }
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return Optional.of(userDetail.getUsername());
    }
}
