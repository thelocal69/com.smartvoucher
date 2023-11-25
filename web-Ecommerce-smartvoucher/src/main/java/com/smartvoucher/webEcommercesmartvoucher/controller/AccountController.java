package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.ResetPasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.event.SignUpCompleteEvent;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    private final IAccountService accountService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AccountController(final IAccountService accountService,
                             final ApplicationEventPublisher applicationEventPublisher) {
        this.accountService = accountService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping("/api/verify_email")
    public ResponseEntity<ResponseObject> verifyEmail(@RequestParam("token") String token){
        log.info("Verify email is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Verify email is completed !",
                        accountService.verifyEmail(token)
                )
        );
    }

    @PostMapping("/api/signin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> signin(@RequestParam String email, @RequestParam String password) {
        log.info("Sign-in is successfully !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Sign-in is successfully !",
                        this.accountService.token(email, password)
                )
        );
    }

    @PostMapping("/api/refresh_token")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> refreshToken(HttpServletRequest request, HttpServletResponse response){
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
    public ResponseEntity<ResponseObject> SignUp(@RequestBody @Valid SignUpDTO signUpDTO, final HttpServletRequest request) {
        signUpDTO.setRoleName("ROLE_USER");
        String applicationURL = "http://localhost:3000/email-verification";
        this.applicationEventPublisher.publishEvent(new SignUpCompleteEvent(this.accountService.SignUp(signUpDTO), applicationURL));
        log.info("Success!  Please, check your email for to complete your registration");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Success!  Please, check your email for to complete your registration",
                        "Success!  Please, check your email for to complete your registration"
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
