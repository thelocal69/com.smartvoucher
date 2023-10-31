package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.RoleDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleService;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<?> getAllRole(){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.rolesService.getAllRole());
    }

    @PostMapping()
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> insertRole(@RequestBody @Valid RoleDTO roleDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.rolesService.insertRole(roleDTO));
    }

    @PutMapping()
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateRole(@RequestBody @Valid RoleDTO roleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.rolesService.updateRole(roleDTO));
    }

    @DeleteMapping()
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteRole(@RequestParam long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.rolesService.deleteRole(id));
    }
}
