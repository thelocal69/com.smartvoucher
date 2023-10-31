package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;

    @Autowired
    public AccountController(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/signin")
    public ResponseEntity<ResponseObject> signin(@RequestParam String email, @RequestParam String password){
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "OK",
                        accountService.token(email, password)
                )
        );
    }
}
