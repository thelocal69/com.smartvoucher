package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface IAccountService {
    String token(String email, String password);
    ResponseObject SignUp(SignUpDTO signUpDTO);
}
