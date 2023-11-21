package com.smartvoucher.webEcommercesmartvoucher.provider;

import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.UserNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.exception.VerificationTokenException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IRoleUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final IRoleUserRepository roleUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public CustomAuthenticationProvider(final IRoleUserRepository roleUserRepository,
                                        final PasswordEncoder passwordEncoder) {
        this.roleUserRepository = roleUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        RolesUsersEntity users = roleUserRepository.findOneByEmailAndProvider(username, Provider.local.name());
        if (users != null){
            if (users.getIdUser().isEnable()){
                if (passwordEncoder.matches(password, users.getIdUser().getPwd())){
                    List<GrantedAuthority> roles = new ArrayList<>();
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                            users.getIdRole().getName()
                    );
                    roles.add(grantedAuthority);
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            username, users.getIdUser().getPwd(), roles
                    );
                    SecurityContextHolder.getContext().setAuthentication(token);
                    return token;
                }else {
                    log.info("Username or password not exist !");
                    throw new UserNotFoundException(404, "Username or password not exist !");
                }
            }else {
                log.info("User is disabled !");
                throw new VerificationTokenException(401, "User is disabled !");
            }
        }else {
            log.info("User not found or not exist !");
            throw new UserNotFoundException(404, "User not found or not exist !");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
