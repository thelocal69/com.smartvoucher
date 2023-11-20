package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.SignUpDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.util.RandomCodeHandler;
import com.smartvoucher.webEcommercesmartvoucher.util.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class  UserConverter {

    private final RandomCodeHandler randomCodeHandler;
    private final PasswordEncoder passwordEncoder;
    private final StringsUtil stringsUtil;
    @Autowired
    public UserConverter( RandomCodeHandler randomCodeHandler,
                          PasswordEncoder passwordEncoder,
                          final StringsUtil stringsUtil){
        this.randomCodeHandler = randomCodeHandler;
        this.passwordEncoder = passwordEncoder;
        this.stringsUtil = stringsUtil;
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

    public List<UserDTO> toUserDTOList(List<UserEntity> userEntityList){
        return userEntityList.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userEntity.getId());
        userEntity.setMemberCode(userDTO.getMemberCode());
        userEntity.setAvatarUrl(userDTO.getAvatarUrl());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setFullName(userDTO.getFullName());
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

    public UserEntity signUp(SignUpDTO signUpDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setMemberCode(randomCodeHandler.generateRandomChars(10));
        userEntity.setPwd(passwordEncoder.encode(signUpDTO.getPassword()));
        userEntity.setAvatarUrl("https://drive.google.com/file/d/1rdcr4ukMLFU2CNmLyrCIixanHu4y31JY/view?usp=drive_link");
        userEntity.setPhone(signUpDTO.getPhone());
        userEntity.setEmail(signUpDTO.getEmail());
        userEntity.setUsername(stringsUtil.getUserNameFormDomain(signUpDTO.getEmail()));
        userEntity.setEnable(false);
        userEntity.setStatus(1);
        userEntity.setProvider(Provider.local.name());
        userEntity.setCreatedAt(signUpDTO.getCreatedAt());
        return userEntity;
    }


    public SignUpDTO signUp(UserDTO userDTO) {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail(userDTO.getEmail());
        signUpDTO.setPhone(userDTO.getPhone());
        return signUpDTO;
    }
    public UserDetailDTO toUserDetailDTO (UserEntity userEntity) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setAvatarUrl(userEntity.getAvatarUrl());
        userDetailDTO.setFirstName(userEntity.getFirstName());
        userDetailDTO.setLastName(userEntity.getLastName());
        userDetailDTO.setFullName(userEntity.getFullName());
        userDetailDTO.setPhone(userEntity.getPhone());
        userDetailDTO.setEmail(userEntity.getEmail());
        userDetailDTO.setAddress(userEntity.getAddress());
        userDetailDTO.setCreatedAt(userEntity.getCreatedAt());
        return userDetailDTO;
    }

    public UserEntity toUserDetailEntity (UserDetailDTO userDetailDTO, UserEntity userEntity) {
        userEntity.setFirstName(userDetailDTO.getFirstName());
        userEntity.setLastName(userDetailDTO.getLastName());
        userEntity.setFullName(userDetailDTO.getFullName());
        userEntity.setUsername(userDetailDTO.getUserName());
        userEntity.setPhone(userDetailDTO.getPhone());
        userEntity.setAddress(userDetailDTO.getAddress());
        return userEntity;
    }

}
