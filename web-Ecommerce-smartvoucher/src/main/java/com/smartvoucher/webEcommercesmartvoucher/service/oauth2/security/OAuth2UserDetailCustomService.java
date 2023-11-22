package com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security;

import com.smartvoucher.webEcommercesmartvoucher.converter.RegisterNewOAuth2UserDetailConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.RoleUsersConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.RegisterNewOAuth2UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.OAuth2LoginException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IRoleUserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.OAuth2UserDetail;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.OAuth2UserDetailFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class OAuth2UserDetailCustomService extends DefaultOAuth2UserService {

    private final IRoleUserRepository roleUserRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RegisterNewOAuth2UserDetailConverter registerConverter;
    private final RoleUsersConverter roleUsersConverter;



    @Autowired
    public OAuth2UserDetailCustomService(final IRoleUserRepository roleUserRepository,
                                         final UserRepository userRepository,
                                         final RoleRepository roleRepository,
                                         final RegisterNewOAuth2UserDetailConverter registerConverter,
                                         final RoleUsersConverter roleUsersConverter) {
        this.roleUserRepository = roleUserRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.registerConverter = registerConverter;
        this.roleUsersConverter = roleUsersConverter;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserDetail oAuth2UserDetail = OAuth2UserDetailFactory.getOAuth2UserDetail(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserDetail.getEmail())) {
            log.info("Email not found from OAuth2 provider");
            throw new OAuth2LoginException(403, "Email not found from OAuth2 provider");
        }
        UserEntity user = userRepository.findByEmailAndProvider(oAuth2UserDetail.getEmail(), Provider.google.name());
        UserEntity userEntity;
        if(user != null) {
            if(!user.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
                log.info("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
                throw new OAuth2LoginException(403, "Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            userEntity = updateExistingUser(user, oAuth2UserDetail);
        } else {
            userEntity = registerNewUser(oAuth2UserRequest, oAuth2UserDetail);
            UserEntity newUSer = userRepository.findByEmailAndProvider(userEntity.getEmail(), Provider.google.name());
            RoleEntity roleEntity = roleRepository.findOneByName("ROLE_USER");
            RolesUsersDTO rolesUsersDTO = roleUsersConverter.toRoleUserDTO(newUSer, roleEntity);
            this.roleUserRepository.save(roleUsersConverter.toRoleUserEntity(rolesUsersDTO));
        }

        return OAuth2UserDetailCustom.create(userEntity, oAuth2User.getAttributes());
    }

    private UserEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetail oAuth2UserDetail) {
        RegisterNewOAuth2UserDetailDTO registerNewOAuth2UserDetailDTO = registerConverter.toRegisterNewOAuth2UserDetailDTO(
                oAuth2UserRequest, oAuth2UserDetail
        );
        return userRepository.save(registerConverter.toUsersEntity(registerNewOAuth2UserDetailDTO));
    }

    private UserEntity updateExistingUser(UserEntity existingUser, OAuth2UserDetail oAuth2UserDetail) {
        existingUser.setEmail(oAuth2UserDetail.getEmail());
        existingUser.setAvatarUrl(oAuth2UserDetail.getAvatarURL());
        return userRepository.save(existingUser);
    }
}
