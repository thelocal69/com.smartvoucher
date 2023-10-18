package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.RoleDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;

public interface IRoleService {

    ResponseObject getAllRole() throws Exception;

    ResponseObject insertRole(RoleDTO roleDTO) throws Exception;

    ResponseObject updateRole(RoleDTO roleDTO) throws Exception;

    ResponseObject deleteRole(long id) throws Exception;
}
