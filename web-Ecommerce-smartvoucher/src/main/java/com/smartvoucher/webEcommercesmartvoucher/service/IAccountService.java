package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.*;
import com.smartvoucher.webEcommercesmartvoucher.dto.token.RefreshTokenDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;

public interface IAccountService {
    RefreshTokenDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws CertificateException;
    ResponseObject signInAdmin(SignInDTO signInDTO);
    ResponseObject signInUser(SignInDTO signInDTO);
    ResponseObject signInGoogle(OAuth2TokenDTO oAuth2TokenDTO);
    String SignUp(SignUpDTO signUpDTO) throws MessagingException, UnsupportedEncodingException;
    String verifyEmail(SignUpDTO signUpDTO);
    String forgotPassword(String email) throws MessagingException, UnsupportedEncodingException;
    String setPassword(ResetPasswordDTO resetPasswordDTO);
    String resendActiveAccount(String email) throws MessagingException, UnsupportedEncodingException;
}
