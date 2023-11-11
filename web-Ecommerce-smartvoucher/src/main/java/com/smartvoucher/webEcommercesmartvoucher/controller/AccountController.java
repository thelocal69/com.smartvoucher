package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;

    @Autowired
    public AccountController(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/signin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> signin(@RequestParam String email, @RequestParam String password) {
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
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
    public ResponseEntity<ResponseObject> SignUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        signUpDTO.setRoleName("ROLE_USER");
        return ResponseEntity.status(HttpStatus.OK).body(
                this.accountService.SignUp(signUpDTO));
    }



}
