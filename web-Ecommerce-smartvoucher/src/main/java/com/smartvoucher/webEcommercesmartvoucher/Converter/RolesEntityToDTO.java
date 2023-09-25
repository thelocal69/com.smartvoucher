package com.smartvoucher.webEcommercesmartvoucher.Converter;

import com.smartvoucher.webEcommercesmartvoucher.DTO.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.RolesEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RolesEntityToDTO {
    public List<RolesDTO> findAllRole(List<RolesEntity> list) {

        List<RolesDTO> listRole = new ArrayList<>();

        for (RolesEntity data : list) {

            RolesDTO rolesDTO = new RolesDTO();
            rolesDTO.setId(data.getId());
            rolesDTO.setName(data.getName());
            rolesDTO.setRoleCode(data.getRoleCode());
            rolesDTO.setUpdatedAt(data.getUpdatedAt());
            rolesDTO.setCreatedAt(data.getCreatedAt());
            rolesDTO.setCreatedBy(data.getCreatedBy());
            rolesDTO.setUpdatedBy(data.getUpdatedBy());

            listRole.add(rolesDTO);
        }

        return listRole;
    }
}
