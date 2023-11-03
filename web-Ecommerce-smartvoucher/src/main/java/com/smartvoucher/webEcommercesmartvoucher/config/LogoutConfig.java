package com.smartvoucher.webEcommercesmartvoucher.config;

import com.smartvoucher.webEcommercesmartvoucher.entity.token.Tokens;
import com.smartvoucher.webEcommercesmartvoucher.repository.token.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogoutConfig implements LogoutHandler {

    private final ITokenRepository tokenRepository;

    @Autowired
    public LogoutConfig(final ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        final String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            final String token = headerValue.substring(7);
            Tokens storedTokens = tokenRepository.findByToken(token).orElse(null);
            if (storedTokens != null){
                storedTokens.setExpired(true);
                storedTokens.setRevoke(true);
                this.tokenRepository.save(storedTokens);
            }
        }
    }
}
