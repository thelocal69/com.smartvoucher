package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface IRoleService {

    ResponseObject getAllRole() throws Exception;

    ResponseObject insertRole(RolesDTO rolesDTO) throws Exception;

    ResponseObject updateRole(RolesDTO rolesDTO) throws Exception;

    ResponseObject deleteRole(long id) throws Exception;
}
