package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.service.IRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role_user")
public class RoleUserController {
    private final IRoleUserService roleUserService;

    @Autowired
    public RoleUserController(final IRoleUserService roleUserService) {
        this.roleUserService = roleUserService;
    }
}
