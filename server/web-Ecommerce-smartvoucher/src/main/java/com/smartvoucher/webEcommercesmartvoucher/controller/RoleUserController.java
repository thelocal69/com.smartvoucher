package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/role_user")
public class RoleUserController {
    private final IRoleUserService roleUserService;

    @Autowired
    public RoleUserController(final IRoleUserService roleUserService) {
        this.roleUserService = roleUserService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllRoleUser(){
        log.info("Get all list role_user is completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all list Role_User is complete",
                        roleUserService.getAllRoleUser()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertRoleUser(@RequestBody RolesUsersDTO rolesUsersDTO){
        log.info("Insert is completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert is completed",
                        roleUserService.insert(rolesUsersDTO)
                )
        );
    }

    @DeleteMapping("/api/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> delete(@RequestBody RolesUsersDTO rolesUsersDTO){
        this.roleUserService.delete(rolesUsersDTO);
        log.info("Delete is completed");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Delete is completed",
                        ""
                )
        );
    }

}
