package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;
    private final AccountService signUpService;

    @Autowired
    public AccountController(final IAccountService accountService,
                             final AccountService signUpService) {
        this.accountService = accountService;
        this.signUpService = signUpService;
    }

    @PostMapping("/api/signin")
    public ResponseEntity<ResponseObject> signin(@RequestParam String email, @RequestParam String password) {
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Sign-in is successfully !",
                        accountService.token(email, password)
                )
        );
    }

    @PostMapping("/api/signup")
    public ResponseEntity<ResponseObject> SignUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        signUpDTO.setRoleName("ROLE_USER");
        return ResponseEntity.status(HttpStatus.OK).body(
                this.signUpService.SignUp(signUpDTO));
    }

}
