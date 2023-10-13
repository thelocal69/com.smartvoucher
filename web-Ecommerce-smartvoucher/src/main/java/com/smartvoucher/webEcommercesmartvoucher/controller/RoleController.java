package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllRole() {

        ResponseObject responseObject =rolesService.getAllRole();

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PostMapping()
    public ResponseEntity<?> insertRole(@RequestBody RolesDTO rolesDTO) {

        ResponseObject responseObject = rolesService.insertRole(rolesDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PutMapping()
    public ResponseEntity<?> updateRole(@RequestBody RolesDTO rolesDTO) {

        ResponseObject responseObject = rolesService.updateRole(rolesDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteRole(@RequestParam long id) {

        ResponseObject responseObject = rolesService.deleteRole(id);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }
}
