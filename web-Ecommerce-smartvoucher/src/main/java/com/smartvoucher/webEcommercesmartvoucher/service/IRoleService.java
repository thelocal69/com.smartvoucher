package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.RoleDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import org.springframework.transaction.annotation.Transactional;

public interface IRoleService {
    ResponseObject getAllRole();
    ResponseObject insertRole(RoleDTO roleDTO);
    ResponseObject updateRole(RoleDTO roleDTO);
    ResponseObject deleteRole(long id);
}
