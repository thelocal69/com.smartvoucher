package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.UsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UsersEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersConverter {

    public UsersDTO toUserDTO(UsersEntity usersEntity) {
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setId(usersEntity.getId());
            usersDTO.setMemberCode(usersEntity.getMemberCode());
            usersDTO.setFirstName(usersEntity.getFirstName());
            usersDTO.setLastName(usersEntity.getLastName());
            usersDTO.setFullName(usersEntity.getFullName());
            usersDTO.setUserName(usersEntity.getUsername());
            usersDTO.setPhone(usersEntity.getPhone());
            usersDTO.setEmail(usersEntity.getEmail());
            usersDTO.setStatus(usersEntity.getStatus());
            usersDTO.setAddress(usersEntity.getAddress());
            usersDTO.setCreatedBy(usersEntity.getCreatedBy());
            usersDTO.setUpdatedBy(usersEntity.getUpdatedBy());
            usersDTO.setCreatedAt(usersEntity.getCreatedAt());
            usersDTO.setUpdatedAt(usersEntity.getUpdatedAt());
        return usersDTO;
    }

    public UsersEntity toUserEntity(UsersDTO usersDTO) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(usersDTO.getId());
        usersEntity.setMemberCode(usersDTO.getMemberCode());
        usersEntity.setFirstName(usersDTO.getFirstName());
        usersEntity.setLastName(usersDTO.getLastName());
        usersEntity.setFullName(usersDTO.getFullName());
        usersEntity.setUsername(usersDTO.getUserName());
        usersEntity.setPhone(usersDTO.getPhone());
        usersEntity.setEmail(usersDTO.getEmail());
        usersEntity.setStatus(usersDTO.getStatus());
        usersEntity.setAddress(usersDTO.getAddress());
        usersEntity.setCreatedBy(usersDTO.getCreatedBy());
        usersEntity.setUpdatedBy(usersDTO.getUpdatedBy());
        usersEntity.setCreatedAt(usersDTO.getCreatedAt());
        usersEntity.setUpdatedAt(usersDTO.getUpdatedAt());
        return usersEntity;
    }
}
