package com.smartvoucher.webEcommercesmartvoucher.Controller;

import com.smartvoucher.webEcommercesmartvoucher.DTO.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.DTO.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.Payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.Service.UsersService;
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

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("200");
        baseResponse.setMessage("List User");
        baseResponse.setData(listUser);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
