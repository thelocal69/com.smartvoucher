package com.smartvoucher.webEcommercesmartvoucher.filter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.smartvoucher.webEcommercesmartvoucher.exception.JwtFilterException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseToken;
import com.smartvoucher.webEcommercesmartvoucher.util.JWTHelper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTHelper jwtHelper;
    private final Gson gson;

    @Autowired
    public JWTFilter(final JWTHelper jwtHelper,
                     final Gson gson) {
        this.jwtHelper = jwtHelper;
        this.gson = gson;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")){
            final String token = headerValue.substring(7);
                if (jwtHelper.validationToke(token)){
                    final String data = jwtHelper.parserToken(token);
                    String reqPath = request.getRequestURI();
                    if (data != null && !data.isEmpty()){
                        if (!reqPath.equals("/account/api/refresh_token")){
                            try {
                                ResponseToken responseToken = gson.fromJson(data, ResponseToken.class);
                                String newData = gson.toJson(responseToken.getRoles());
                                Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>(){}.getType();
                                List<GrantedAuthority> roles = gson.fromJson(newData, listType);
                                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                                        responseToken.getUsername(), "", roles
                                );
                                SecurityContext contextHolder = SecurityContextHolder.getContext();
                                contextHolder.setAuthentication(userToken);
                            }catch (JsonSyntaxException | IllegalStateException e){
                                log.info("Not accept type !", e);
                                throw new JsonSyntaxException("Not accept type !", e);
                            }
                        }
                    }else {
                        log.info("Data is not exist !");
                        throw new JwtFilterException(403, "Data is not exist !", null);
                    }
                }
            }
        filterChain.doFilter(request, response);
    }
}
