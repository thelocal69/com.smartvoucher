package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.ResetPasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignInDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.token.RefreshTokenDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseAuthentication;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IAccountService {
    RefreshTokenDTO refreshToken(HttpServletRequest request, HttpServletResponse response);
    ResponseObject signInAdmin(SignInDTO signInDTO);
    ResponseObject signInUser(String email, String password);
    SignUpDTO SignUp(SignUpDTO signUpDTO);
    void saveUserVerificationToken(UserEntity user, String token);
    String verifyEmail(String token);
    String validateToken(String verifyToken);
    String forgotPassword(String email) throws MessagingException, UnsupportedEncodingException;
    String setPassword(ResetPasswordDTO resetPasswordDTO);
}
