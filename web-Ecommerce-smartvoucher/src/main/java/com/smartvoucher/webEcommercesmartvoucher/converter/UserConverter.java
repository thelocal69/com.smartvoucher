package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO toUserDTO(UserEntity userEntity) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userEntity.getId());
            userDTO.setMemberCode(userEntity.getMemberCode());
            userDTO.setFirstName(userEntity.getFirstName());
            userDTO.setLastName(userEntity.getLastName());
            userDTO.setFullName(userEntity.getFullName());
            userDTO.setUserName(userEntity.getUsername());
            userDTO.setPhone(userEntity.getPhone());
            userDTO.setEmail(userEntity.getEmail());
            userDTO.setStatus(userEntity.getStatus());
            userDTO.setAddress(userEntity.getAddress());
            userDTO.setCreatedBy(userEntity.getCreatedBy());
            userDTO.setUpdatedBy(userEntity.getUpdatedBy());
            userDTO.setCreatedAt(userEntity.getCreatedAt());
            userDTO.setUpdatedAt(userEntity.getUpdatedAt());
        return userDTO;
    }

    public UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setMemberCode(userDTO.getMemberCode());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setFullName(userDTO.getFullName());
        userEntity.setUsername(userDTO.getUserName());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setStatus(userDTO.getStatus());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setCreatedBy(userDTO.getCreatedBy());
        userEntity.setUpdatedBy(userDTO.getUpdatedBy());
        userEntity.setCreatedAt(userDTO.getCreatedAt());
        userEntity.setUpdatedAt(userDTO.getUpdatedAt());
        return userEntity;
    }
}
