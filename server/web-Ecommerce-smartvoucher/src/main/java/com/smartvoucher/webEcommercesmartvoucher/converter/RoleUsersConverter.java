package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.keys.RolesUsersKeys;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public RolesUsersDTO toRoleUserDTO(RolesUsersEntity rolesUsersEntity){
        return RolesUsersDTO.builder()
                .idUser(rolesUsersEntity.getRoleUserKeys().getIdUser())
                .idRole(rolesUsersEntity.getRoleUserKeys().getIdRole())
                .memberCode(rolesUsersEntity.getIdUser().getMemberCode())
                .roleName(rolesUsersEntity.getIdRole().getRoleCode())
                .createdBy(rolesUsersEntity.getCreatedBy())
                .createdAt(rolesUsersEntity.getCreatedAt())
                .updatedBy(rolesUsersEntity.getUpdateBy())
                .updatedAt(rolesUsersEntity.getUpdateAt())
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
        rolesUsersEntity.setRoleUserKeys(toRoleUserKeys(rolesUsersDTO));
        rolesUsersEntity.setCreatedBy(rolesUsersDTO.getCreatedBy());
        rolesUsersEntity.setCreatedAt(rolesUsersDTO.getCreatedAt());
        rolesUsersEntity.setUpdateBy(rolesUsersDTO.getUpdatedBy());
        rolesUsersEntity.setUpdateAt(rolesUsersDTO.getUpdatedAt());
        return rolesUsersEntity;
    }

    public List<RolesUsersDTO> toRoleUserDTOList(List<RolesUsersEntity> rolesUsersEntityList){
        return rolesUsersEntityList.stream().map(this::toRoleUserDTO).collect(Collectors.toList());
    }
}
