package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface IRoleService {

    ResponseObject getAllRole();

    ResponseObject insertRole(RolesDTO rolesDTO);

    ResponseObject updateRole(RolesDTO rolesDTO);

    ResponseObject deleteRole(long id);
}
