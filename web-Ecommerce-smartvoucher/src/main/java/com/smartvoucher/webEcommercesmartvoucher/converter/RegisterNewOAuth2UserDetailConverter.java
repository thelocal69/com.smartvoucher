package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.RegisterNewOAuth2UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.OAuth2UserDetail;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterNewOAuth2UserDetailConverter {
    public RegisterNewOAuth2UserDetailDTO toRegisterNewOAuth2UserDetailDTO(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetail oAuth2UserDetail){
        RegisterNewOAuth2UserDetailDTO registerNewOAuth2UserDetailDTO = new RegisterNewOAuth2UserDetailDTO();
        registerNewOAuth2UserDetailDTO.setMemberCode(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        registerNewOAuth2UserDetailDTO.setAvatarURL(oAuth2UserDetail.getAvatarURL());
        registerNewOAuth2UserDetailDTO.setEmail(oAuth2UserDetail.getEmail());
        registerNewOAuth2UserDetailDTO.setUsername(oAuth2UserDetail.getName());
        registerNewOAuth2UserDetailDTO.setFirstName(oAuth2UserDetail.getFirstName());
        registerNewOAuth2UserDetailDTO.setLastName(oAuth2UserDetail.getLastName());
        registerNewOAuth2UserDetailDTO.setFullName(oAuth2UserDetail.getLastName()+" "+ oAuth2UserDetail.getFirstName());
        registerNewOAuth2UserDetailDTO.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        registerNewOAuth2UserDetailDTO.setEnable(true);
        registerNewOAuth2UserDetailDTO.setStatus(1);
        registerNewOAuth2UserDetailDTO.setRoleName("ROLE_USER");
        return registerNewOAuth2UserDetailDTO;
    }

    public UserEntity toUsersEntity(RegisterNewOAuth2UserDetailDTO registerNewOAuth2UserDetailDTO){
        UserEntity user = new UserEntity();
        user.setMemberCode(registerNewOAuth2UserDetailDTO.getMemberCode());
        user.setEmail(registerNewOAuth2UserDetailDTO.getEmail());
        user.setUsername(registerNewOAuth2UserDetailDTO.getUsername());
        user.setAvatarUrl(registerNewOAuth2UserDetailDTO.getAvatarURL());
        user.setLastName(registerNewOAuth2UserDetailDTO.getLastName());
        user.setFirstName(registerNewOAuth2UserDetailDTO.getFirstName());
        user.setFullName(registerNewOAuth2UserDetailDTO.getFullName());
        user.setProvider(registerNewOAuth2UserDetailDTO.getProvider());
        user.setEnable(registerNewOAuth2UserDetailDTO.isEnable());
        user.setStatus(registerNewOAuth2UserDetailDTO.getStatus());
        return user;
    }
}
