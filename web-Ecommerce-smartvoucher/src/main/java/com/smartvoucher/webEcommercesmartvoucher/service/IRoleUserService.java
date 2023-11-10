package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;

public interface IRoleUserService {
    RolesUsersDTO insert(RolesUsersDTO rolesUsersDTO);
    void delete(RolesUsersDTO rolesUsersDTO);
}
