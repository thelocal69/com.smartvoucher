package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChangePasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IUserService;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.OAuth2UserDetailCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/all")
    public ResponseEntity<ResponseObject>getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "get all user completed !",
                userService.getAllUser()
        )
        );
    }
    @GetMapping("/api/auth2/infor")
    public ResponseEntity<ResponseObject> getUser(@AuthenticationPrincipal OAuth2UserDetailCustom oAuth2User){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Success !",
                        userService.getInformationOauth2User(oAuth2User)
                )
        );
    }

    @PostMapping("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        userService.uploadUserImages(fileName).getWebViewLink()
                )
        );
    }
    @GetMapping("/api/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get user's info successfully",
                        userService.getUserById(id)
                )
        );
    }

    @GetMapping("/api/infor")
    public ResponseEntity<ResponseObject>getLoginInfor(Principal connectedUser){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "get Information is completed !",
                        userService.getInformationLoginUser(connectedUser)
                )
        );
    }

    @PutMapping("/api/edit")
    public ResponseEntity<ResponseObject>editProfile(
            @RequestParam MultipartFile avatar,
            @RequestParam String firsName,
            @RequestParam String lastName,
            @RequestParam String fullName,
            @RequestParam String userName,
            @RequestParam String phone,
            @RequestParam String address,
            Principal connectedUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Update profile is completed !",
                        userService.editUserProfile(
                                avatar, firsName, lastName,
                                fullName, userName, phone,
                                address, connectedUser
                        )
                )
        );
    }

    @PutMapping("/api/change_pwd")
    public ResponseEntity<ResponseObject> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,
                                                         Principal connectedUser){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "Change password is completed !",
                        this.userService.changePassword(changePasswordDTO, connectedUser))
        );
    }
}
