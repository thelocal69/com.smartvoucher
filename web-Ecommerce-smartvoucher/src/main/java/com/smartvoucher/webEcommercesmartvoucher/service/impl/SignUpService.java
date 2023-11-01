package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ISignUpService;
import org.springframework.stereotype.Service;

@Service
public class SignUpService implements ISignUpService {
    private final UserRepository userRepository;

    public SignUpService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public ResponseObject SignUp(SignUpDTO signUpDTO) {
        UserEntity userEntity = userRepository.
        return null;
    }
}
