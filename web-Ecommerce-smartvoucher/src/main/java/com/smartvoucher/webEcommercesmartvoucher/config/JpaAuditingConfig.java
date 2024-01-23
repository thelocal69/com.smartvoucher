package com.smartvoucher.webEcommercesmartvoucher.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<String> auditorProvider(){
        //tracking date time
        return new AuditorAwareImpl();
    }

    @PrePersist
    public void prePersist(Object o) {

    }

    @PreUpdate
    public void preUpdate(Object o) {

    }

    @PreRemove
    public void preRemove(Object o) {

    }

    @PostLoad
    public void postLoad(Object o) {

    }

    @PostRemove
    public void postRemove(Object o) {

    }

    @PostUpdate
    public void postUpdate(Object o) {

    }

    @PostPersist
    public void postPersist(Object o) {

    }

    public static class AuditorAwareImpl implements AuditorAware<String> {

        //use nested class
        @Override
        public @NonNull Optional<String> getCurrentAuditor() {
            //tracking user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String nickName = username;
            if (username.contains("@")){
                int index = username.indexOf("@");
                nickName = username.substring(0 ,index);
            }
            if (!authentication.isAuthenticated()){
                return Optional.empty();
            }
            return Optional.of(nickName);
        }
    }
}
