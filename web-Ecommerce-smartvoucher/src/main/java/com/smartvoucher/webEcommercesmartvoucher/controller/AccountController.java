package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.OAuth2TokenDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.ResetPasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignInDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    private final IAccountService accountService;

    @Autowired
    public AccountController(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping ("/api/verify_email")
    public ResponseEntity<ResponseObject> verifyEmail(@RequestBody SignUpDTO signUpDTO){
        log.info("Verify email is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Verify email is completed !",
                        accountService.verifyEmail(signUpDTO)
                )
        );
    }

    @PostMapping("/api/signin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> signin(@RequestBody SignInDTO signInDTO) {
        log.info("Sign-in is successfully !");
        return new ResponseEntity<>(accountService.signInUser(signInDTO), HttpStatus.OK);
    }

    @PostMapping("/api/oauth2/signin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> googleSignin(@RequestBody OAuth2TokenDTO oAuth2TokenDTO) {
        log.info("Sign-in is successfully !");
        return new ResponseEntity<>(accountService.signInGoogle(oAuth2TokenDTO), HttpStatus.OK);
    }

    @PostMapping("/api/signinAdmin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> signinAdmin(@RequestBody SignInDTO signInDTO) {
        log.info("Sign-in admin is successfully !");
        return new ResponseEntity<>(accountService.signInAdmin(signInDTO), HttpStatus.OK);
    }

    @PostMapping("/api/refresh_token")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> refreshToken(HttpServletRequest request, HttpServletResponse response) throws CertificateException {
        log.info("Refresh token is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Refresh token is completed !",
                        this.accountService.refreshToken(request, response)
                )
        );
    }

    @PostMapping("/api/signup")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> SignUp(@RequestBody @Valid SignUpDTO signUpDTO) throws MessagingException, UnsupportedEncodingException {
        signUpDTO.setRoleName("ROLE_USER");
        log.info("Success!  Please, check your email for to complete your registration");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Success!  Please, check your email for to complete your registration",
                        this.accountService.SignUp(signUpDTO)
                )
        );
    }

    @PostMapping("/api/resend_token")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> resendToken(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        log.info("Verification code is resend to your email ! please check email to activation account again !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Verification code is resend to your email ! please check email to activation account again !",
                        this.accountService.resendActiveAccount(email)
                )
        );
    }

    @PostMapping("/api/forgot_password")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> forgotPassword(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        log.info("Send reset password is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Send reset password is completed !",
                        this.accountService.forgotPassword(email)
                )
        );
    }

    @PutMapping("/api/set_password")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> setPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        log.info("Send reset password is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Send reset password is completed !",
                        this.accountService.setPassword(resetPasswordDTO)
                )
        );
    }
}
