package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.gson.Gson;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseToken;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import com.smartvoucher.webEcommercesmartvoucher.util.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {

    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    private final Gson gson;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public AccountService(final AuthenticationManager authenticationManager,
                          final JWTHelper jwtHelper,
                          final Gson gson,
                          final UserRepository userRepository,
                          final UserConverter userConverter) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.gson = gson;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public String token(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password
        );
        authenticationManager.authenticate(authenticationToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        ResponseToken data = new ResponseToken();
        data.setUsername(email);
        data.setRoles(roles);
        return jwtHelper.generateToken(gson.toJson(data));
    }

    @Override
    public ResponseObject SignUp(SignUpDTO signUpDTO) {
        if (userRepository.findByEmailOrPhone(
                signUpDTO.getEmail(),
                signUpDTO.getPhone()).isEmpty()) {
            if(userRepository.findByUsername(signUpDTO.getUserName()) == null) {
                return new ResponseObject(
                        200,
                        "SignUp success!",
                        userConverter.toUserDTO(userRepository.save(userConverter.signUp(signUpDTO))));
            } else {
                throw new DuplicationCodeException(400, "UserName is available! please try again.");
            }
        } else {
            throw new DuplicationCodeException(400, "Email or Phone is available! please try again.");
        }
    }
}
