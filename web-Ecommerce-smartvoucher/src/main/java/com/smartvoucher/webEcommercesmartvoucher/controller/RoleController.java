package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.RoleDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final IRoleService rolesService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.rolesService = roleService;
    }

    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllRole() throws Exception {

        ResponseObject responseObject =rolesService.getAllRole();

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PostMapping()
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> insertRole(@RequestBody RoleDTO roleDTO) throws Exception {

        ResponseObject responseObject = rolesService.insertRole(roleDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @PutMapping()
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO) throws Exception {

        ResponseObject responseObject = rolesService.updateRole(roleDTO);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }

    @DeleteMapping()
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> deleteRole(@RequestParam long id) throws Exception {

        ResponseObject responseObject = rolesService.deleteRole(id);

        return ResponseEntity.status(responseObject.getStatusCode()).body(responseObject);
    }
}
