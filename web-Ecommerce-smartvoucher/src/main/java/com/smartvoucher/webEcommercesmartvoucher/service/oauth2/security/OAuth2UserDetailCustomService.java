package com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security;

import com.smartvoucher.webEcommercesmartvoucher.converter.RegisterNewOAuth2UserDetailConverter;
import com.smartvoucher.webEcommercesmartvoucher.converter.RoleUsersConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.RegisterNewOAuth2UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.RolesUsersDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.RoleEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesUsersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.OAuth2LoginException;
import com.smartvoucher.webEcommercesmartvoucher.repository.IRoleUserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.RoleRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.OAuth2UserDetail;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.OAuth2UserDetailFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return checkingOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User checkingOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User){
        OAuth2UserDetail oAuth2UserDetail = OAuth2UserDetailFactory.getOAuth2UserDetail(
                userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes()
        );
        if (ObjectUtils.isEmpty(oAuth2UserDetail)){
            throw new OAuth2LoginException(400, "Cannot found oauth 2.0 from user properties !");
        }
        UserEntity user = userRepository.findByEmailAndProvider(
                oAuth2UserDetail.getEmail(), userRequest.getClientRegistration().getRegistrationId()
        );
        List<GrantedAuthority> roles = new ArrayList<>();
        if (user != null){
            if (!user.getProvider().equals(userRequest.getClientRegistration().getRegistrationId())
            ){
                throw new OAuth2LoginException(400, "Invalid site sign-in with "+ user.getProvider());
            }
        }else {
            UserEntity userDetail = registerNewOAuth2UserDetail(userRequest, oAuth2UserDetail);
            RoleEntity role = roleRepository.findOneByName("ROLE_USER");
            RolesUsersDTO rolesUsersDTO = roleUsersConverter.toRoleUserDTO(userDetail, role);
            this.roleUserRepository.save(roleUsersConverter.toRoleUserEntity(rolesUsersDTO));
        }
        RolesUsersEntity rolesUsers = roleUserRepository.findOneByEmailAndProvider(oAuth2UserDetail.getEmail(), Provider.google.name());
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                rolesUsers.getIdRole().getName()
        );
        roles.add(grantedAuthority);
        return new OAuth2UserDetailCustom(
                rolesUsers.getIdUser().getId(),
                rolesUsers.getIdUser().getEmail(),
                rolesUsers.getIdUser().getPwd(),
                roles
        );
    }

    public UserEntity registerNewOAuth2UserDetail(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetail oAuth2UserDetail){
        RegisterNewOAuth2UserDetailDTO registerOAuth2UserDTO = registerConverter.toRegisterNewOAuth2UserDetailDTO(
                oAuth2UserRequest, oAuth2UserDetail);
        return  userRepository.save(registerConverter.toUsersEntity(registerOAuth2UserDTO));
    }
}
