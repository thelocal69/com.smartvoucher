package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ISignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SignUpService implements ISignUpService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    @Autowired
    public SignUpService(UserRepository userRepository,
                         UserConverter userConverter){
        this.userRepository = userRepository;
        this.userConverter = userConverter;
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
