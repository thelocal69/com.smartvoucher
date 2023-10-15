package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final IRoleService rolesService;

    @Autowired
    public RoleController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllRole() throws Exception {

        ResponseObject responseObject =rolesService.getAllRole();

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PostMapping()
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> insertRole(@RequestBody RolesDTO rolesDTO) throws Exception {

        ResponseObject responseObject = rolesService.insertRole(rolesDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PutMapping()
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateRole(@RequestBody RolesDTO rolesDTO) throws Exception {

        ResponseObject responseObject = rolesService.updateRole(rolesDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @DeleteMapping()
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> deleteRole(@RequestParam long id) throws Exception {

        ResponseObject responseObject = rolesService.deleteRole(id);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }
}
