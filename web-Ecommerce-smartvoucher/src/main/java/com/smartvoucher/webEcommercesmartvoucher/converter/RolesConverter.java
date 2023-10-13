package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RolesConverter {
    public List<RolesDTO> findAllRole(List<RolesEntity> list) {

        List<RolesDTO> listRole = new ArrayList<>();

        for (RolesEntity data : list) {

            RolesDTO rolesDTO = new RolesDTO();
            rolesDTO.setId(data.getId());
            rolesDTO.setName(data.getName());
            rolesDTO.setRoleCode(data.getRoleCode());
            rolesDTO.setCreatedAt(data.getCreatedAt());
            rolesDTO.setUpdatedAt(data.getUpdatedAt());
            rolesDTO.setCreatedBy(data.getCreatedBy());
            rolesDTO.setUpdatedBy(data.getUpdatedBy());

            listRole.add(rolesDTO);
        }

        return listRole;
    }

    public RolesEntity insertRole(RolesDTO rolesDTO) {

        RolesEntity role = new RolesEntity();
        role.setName(rolesDTO.getName());
        role.setRoleCode(rolesDTO.getRoleCode());

        return role;
    }

    public RolesEntity updateRole(RolesDTO rolesDTO, RolesEntity oldRole) {

        if (rolesDTO.getName() != null
                && !rolesDTO.getName().isEmpty()
                && !rolesDTO.getName().substring(5).isEmpty()
                && !Objects.equals(rolesDTO.getName(), oldRole.getName()) ) {
            oldRole.setName(rolesDTO.getName());
        }

        if (rolesDTO.getRoleCode() != null
                && !rolesDTO.getRoleCode().isEmpty()
                && !Objects.equals(rolesDTO.getRoleCode(), oldRole.getRoleCode()) ) {
            oldRole.setRoleCode(rolesDTO.getRoleCode());
        }

        return oldRole;

    }
}
