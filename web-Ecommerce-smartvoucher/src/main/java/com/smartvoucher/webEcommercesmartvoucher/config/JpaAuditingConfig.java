package com.smartvoucher.webEcommercesmartvoucher.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<String> auditorProvider(){
        //tracking date time
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {

        public AuditorAwareImpl(){}

        //use nested class
        @Override
        public @NonNull Optional<String> getCurrentAuditor() {
            //tracking user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            int index = username.indexOf("@");
            String nickName = username.substring(0 ,index);
            if (!authentication.isAuthenticated()){
                return Optional.empty();
            }
            return Optional.of(nickName);
        }
    }
}
