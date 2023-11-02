package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface ISignUpService {
    ResponseObject SignUp(SignUpDTO signUpDTO);
}
