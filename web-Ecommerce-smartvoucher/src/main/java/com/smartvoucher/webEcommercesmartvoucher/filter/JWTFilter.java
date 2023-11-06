package com.smartvoucher.webEcommercesmartvoucher.filter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.smartvoucher.webEcommercesmartvoucher.exception.JwtFilterException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseToken;
import com.smartvoucher.webEcommercesmartvoucher.repository.token.ITokenRepository;
import com.smartvoucher.webEcommercesmartvoucher.util.JWTHelper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTHelper jwtHelper;
    private final Gson gson;
    private final ITokenRepository tokenRepository;

    @Autowired
    public JWTFilter(final JWTHelper jwtHelper,
                     final Gson gson,
                     final ITokenRepository tokenRepository) {
        this.jwtHelper = jwtHelper;
        this.gson = gson;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerValue != null && headerValue.startsWith("Bearer ")){
            final String token = headerValue.substring(7);
            final String data = jwtHelper.parserToken(token);
            if (data != null && !data.isEmpty()){
                boolean isValidTokens = tokenRepository.findByToken(token)
                        .map(tokens ->
                            !tokens.isExpired() && !tokens.isRevoke()
                        ).orElse(false);
                ResponseToken responseToken = gson.fromJson(data, ResponseToken.class);
                String newData = gson.toJson(responseToken.getRoles());
                Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>(){}.getType();
                List<GrantedAuthority> roles = gson.fromJson(newData, listType);
                if (isValidTokens){
                    UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                            responseToken.getUsername(), "", roles
                    );
                    SecurityContext contextHolder = SecurityContextHolder.getContext();
                    contextHolder.setAuthentication(userToken);
                }
            }else {
                throw new JwtFilterException(403, "Data is not exist !");
            }
        }
        filterChain.doFilter(request, response);
    }
}
