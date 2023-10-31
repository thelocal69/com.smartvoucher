package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleUsersConverter {
    public RolesUsersDTO toRoleUserDTO(RolesUsersEntity rolesUsersEntity){
        RolesUsersDTO rolesUsersDTO = new RolesUsersDTO();
        rolesUsersDTO.setMemberCode(rolesUsersEntity.getIdUser().getMemberCode());
        rolesUsersDTO.setRoleCode(rolesUsersEntity.getIdRole().getRoleCode());
        return rolesUsersDTO;
    }
}
