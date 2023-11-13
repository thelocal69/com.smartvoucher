package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.config.SecurityConfig;
import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.WareHouseDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.util.RandomCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class  UserConverter {

    private final RandomCodeHandler randomCodeHandler;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserConverter( RandomCodeHandler randomCodeHandler,
                          PasswordEncoder passwordEncoder){
        this.randomCodeHandler = randomCodeHandler;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO toUserDTO(UserEntity userEntity) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userEntity.getId());
            userDTO.setMemberCode(userEntity.getMemberCode());
            userDTO.setAvatarUrl(userEntity.getAvatarUrl());
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

    public UserEntity signUp(SignUpDTO signUpDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setMemberCode(randomCodeHandler.generateRandomChars(10));
        userEntity.setPwd(passwordEncoder.encode(signUpDTO.getPassword()));
        userEntity.setLastName(signUpDTO.getLastName());
        userEntity.setFullName(signUpDTO.getFullName());
        userEntity.setUsername(signUpDTO.getUserName());
        userEntity.setPhone(signUpDTO.getPhone());
        userEntity.setEmail(signUpDTO.getEmail());
        userEntity.setAddress(signUpDTO.getAddress());
        userEntity.setStatus(1);
        return userEntity;
    }


}
