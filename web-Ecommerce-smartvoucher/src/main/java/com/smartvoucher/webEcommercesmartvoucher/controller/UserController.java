package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.BlockUserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.BuyVoucherDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChangePasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput>getAllUser(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField
    ){
        log.info("get all user completed !");
        return new ResponseEntity<>(userService.getAllUser(
                page, limit, sortBy, sortField), HttpStatus.OK);
    }

    @GetMapping("/api/search")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject>searchUserByEmail(
            @RequestParam String email
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "search all user completed !",
                        userService.searchUserByEmail(email)
                )
        );
    }

    @GetMapping("/api/profile")
    public ResponseEntity<ResponseObject>profile(Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "get Information is completed !",
                        userService.getInformationLoginUser(principal)
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

    @PostMapping("/api/uploadAdmin")
    public ResponseEntity<ResponseObject> uploadFilesAdmin(@RequestParam MultipartFile fileName, Principal connectedUser){
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

    @PutMapping("/api/buy_voucher")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject>buyVoucher(
            @RequestBody BuyVoucherDTO buyVoucherDTO,
            Principal connectedUser
    ){
        log.info("buy voucher is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Buy voucher is completed !",
                        userService.buyTicketByBalance(
                         buyVoucherDTO, connectedUser
                        )
                )
        );
    }

    @PutMapping("/api/block/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject>blockUser(
            @RequestBody BlockUserDTO blockUserDTO,
            @PathVariable Long id
    ){
        blockUserDTO.setId(id);
        log.info("Block user is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Block user is completed !",
                        userService.blockUser(blockUserDTO)
                        )
                );
    }

    @PutMapping("/api/editAdmin")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject>editProfileAdmin(
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
