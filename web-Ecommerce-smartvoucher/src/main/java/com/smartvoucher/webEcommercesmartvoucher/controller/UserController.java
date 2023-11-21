package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChangePasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IUserService;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.OAuth2UserDetailCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject>getAllUser(){
        log.info("get all user completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "get all user completed !",
                userService.getAllUser()
        )
        );
    }
    @GetMapping("/api/auth2/infor")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getUser(@AuthenticationPrincipal OAuth2UserDetailCustom oAuth2User){
        log.info("Success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Success !",
                        userService.getInformationOauth2User(oAuth2User)
                )
        );
    }

    @PostMapping("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName, Principal connectedUser){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        userService.uploadUserImages(fileName, connectedUser)
                )
        );
    }
    @GetMapping("/api/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getUserById(@PathVariable long id) {
        log.info("Get user's info successfully");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get user's info successfully",
                        userService.getUserById(id)
                )
        );
    }

    @GetMapping("/api/infor")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject>getLoginInfor(Principal connectedUser){
        log.info("get Information is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "get Information is completed !",
                        userService.getInformationLoginUser(connectedUser)
                )
        );
    }

    @PutMapping("/api/edit")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject>editProfile(
            @RequestBody @Valid UserDetailDTO userDetailDTO,
            Principal connectedUser
    ){
        log.info("Update profile is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Update profile is completed !",
                        userService.editUserProfile(
                                userDetailDTO, connectedUser
                        )
                )
        );
    }

    @PutMapping("/api/change_pwd")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,
                                                         Principal connectedUser){
        log.info("Change password is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "Change password is completed !",
                        this.userService.changePassword(changePasswordDTO, connectedUser))
        );
    }
}
