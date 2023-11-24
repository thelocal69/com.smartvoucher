package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;

import java.util.List;

public interface IRoleUserService {
    List<RolesUsersDTO> getAllRoleUser();
    RolesUsersDTO insert(RolesUsersDTO rolesUsersDTO);
    void delete(RolesUsersDTO rolesUsersDTO);
}
