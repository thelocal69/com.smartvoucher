package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RolesService rolesService;

    @Autowired
    public RoleController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    public ResponseEntity<?> getAllRole() {

        List<RolesDTO> list = rolesService.findAllRole();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(200);
        baseResponse.setMessage("List Role");
        baseResponse.setData(list);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
