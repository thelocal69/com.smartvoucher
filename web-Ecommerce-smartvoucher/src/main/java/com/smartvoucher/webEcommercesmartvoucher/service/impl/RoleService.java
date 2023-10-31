package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.RoleConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.RoleDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectEmptyException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;
    @Autowired
    public RoleService(RoleRepository roleRepository,
                       RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseObject getAllRole(){
        List<RoleDTO> listRole = new ArrayList<>();
        List<RoleEntity> list = roleRepository.findAll();
        if(!list.isEmpty()) {
            for (RoleEntity data : list) {
                listRole.add(roleConverter.toRoleDTO(data));
            }
            return new ResponseObject(200, "List Roles", listRole);
        } else {
            throw new ObjectEmptyException(406, "List Role is empty");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject insertRole(RoleDTO roleDTO) {
        roleDTO = updateRoleName(roleDTO);
        Optional<RoleEntity> role = roleRepository.findByNameOrRoleCode(roleDTO.getName(), roleDTO.getRoleCode());
        if (role.isEmpty()) {
            return new ResponseObject(200,
                    "Add Role success!",
                    roleConverter.toRoleDTO(roleRepository.save(roleConverter.insertRole(roleDTO))) );
        } else {
            throw new DuplicationCodeException(400, "Role is available, add role fail");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject updateRole(RoleDTO roleDTO){
        roleDTO = updateRoleName(roleDTO);
        RoleEntity oldRole = roleRepository.findByRoleCodeAndId(roleDTO.getRoleCode(), roleDTO.getId());
            if (oldRole != null) {
                List<RoleEntity> checkRoleName = roleRepository.findByNameAndId(roleDTO.getName(), roleDTO.getId());
                if (checkRoleName.isEmpty()) {
                    return new ResponseObject(200,
                            "update Role success",
                            roleConverter.toRoleDTO(roleRepository.save(roleConverter.updateRole(roleDTO, oldRole))));
                } else {
                    throw new DuplicationCodeException(400, "Role is available, please fill again");
                }
            } else {
                throw new ObjectNotFoundException(404, "Role not found, update Role fail");
            }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject deleteRole(long id){
        RoleEntity role = roleRepository.findById(id).orElse(null);
        if(role != null) {
            roleRepository.deleteById(id);
            return new ResponseObject(200, "Delete Role Success", true);
        } else {
            throw new ObjectNotFoundException(404, "Can not delete Role id : " + id);
        }

    }

    public RoleDTO updateRoleName(RoleDTO roleDTO) {
        if (!roleDTO.getName().startsWith("ROLE_")) {
            String roleName = "ROLE_" + roleDTO.getName();
            roleDTO.setName(roleName);
        }
        return roleDTO;
    }


}
