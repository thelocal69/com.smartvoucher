package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    public ResponseEntity<?> getAllUser() {

        List<UsersDTO> listUser = usersService.findAllUser();

        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusCode(200);
        responseObject.setMessage("List User");
        responseObject.setData(listUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}
