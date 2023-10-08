package com.smartvoucher.webEcommercesmartvoucher.converter.entityToDTO;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesEntity;
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
