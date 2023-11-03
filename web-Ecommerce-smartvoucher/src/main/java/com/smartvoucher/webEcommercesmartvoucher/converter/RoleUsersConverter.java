package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.RoleDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.RolesUsersKeys;
import org.springframework.stereotype.Component;

@Component
public class RoleUsersConverter {
    public RolesUsersDTO toRoleUserDTO(UserEntity user, RoleEntity role){
        return RolesUsersDTO.builder()
                .idRole(role.getId())
                .idUser(user.getId())
                .roleName(role.getName())
                .memberCode(user.getMemberCode())
                .build();
    }

    public RolesUsersKeys toRoleUserKeys(RolesUsersDTO rolesUsersDTO){
        return RolesUsersKeys.builder()
                .idRole(rolesUsersDTO.getIdRole())
                .idUser(rolesUsersDTO.getIdUser())
                .build();
    }

    public RolesUsersEntity toRoleUserEntity(RolesUsersDTO rolesUsersDTO){
        RolesUsersEntity rolesUsersEntity = new RolesUsersEntity();
        rolesUsersEntity.setRoles_usersKeys(toRoleUserKeys(rolesUsersDTO));
        return rolesUsersEntity;
    }
}
