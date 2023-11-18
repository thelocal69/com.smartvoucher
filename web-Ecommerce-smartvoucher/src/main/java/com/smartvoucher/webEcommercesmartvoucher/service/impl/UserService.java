package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChangePasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.*;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IUserService;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.OAuth2UserDetailCustom;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final UploadUtil uploadUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final UserConverter userConverter,
                       final UploadUtil uploadUtil,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.uploadUtil = uploadUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public File uploadUserImages(MultipartFile fileName) {
        String folderId = "1jNZ5_XieGUGKYIhNq3P2tflcKYHCwdb5";
        return uploadUtil.uploadImages(fileName, folderId);
    }



    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUser() {
        if (userRepository.findAll().isEmpty()) {
            throw new ObjectNotFoundException(404, "List user is empty !");
        }
        return userConverter.toUserDTOList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getEmail(UserDTO userDTO) {
        if (userRepository.findOneByEmail(userDTO.getEmail()) == null) {
            throw new UserNotFoundException(404, "User not found or not exist !");
        }
        return userConverter.toUserDTO(userRepository.findOneByEmail(userDTO.getEmail()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDTO getUserById(Long id) {
        if (userRepository.findOneByIdAndProvider(id, Provider.local.name()) == null) {
            throw new UserNotFoundException(404, "User not found or not exist !");
        }
        return userConverter.toUserDetailDTO(userRepository.findOneByIdAndProvider(id, Provider.local.name()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String changePassword(ChangePasswordDTO changePasswordDTO, Principal connectedUser) {
        String email =  connectedUser.getName();
        UserEntity user = userRepository.findByEmailAndProvider(email, Provider.local.name());
        //check MK user trong database
        if (!(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPwd()))){
            throw new ChangePasswordException(501, "Wrong password !");
        }
        if (!(changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword()))){
            throw new ChangePasswordException(501, "Password won't match !");
        }
        //update new password
        user.setPwd(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        this.userRepository.save(user);
        return "Change password successfully !";
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDTO getInformationLoginUser(Principal connectedUser) {
        UserEntity user;
        String email = connectedUser.getName();
        if (this.userRepository.findByEmailAndProvider(email, Provider.local.name()) != null) {
            user = userRepository.findByEmailAndProvider(email, Provider.local.name());
            return userConverter.toUserDetailDTO(user);
        }else {
            throw new UserNotFoundException(404, "User not found data");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDTO getInformationOauth2User(OAuth2UserDetailCustom oAuth2UserDetail) {
        UserEntity user;
        String email = oAuth2UserDetail.getUsername();
        if (this.userRepository.findByEmailAndProvider(email, Provider.google.name()) != null) {
            user = userRepository.findByEmailAndProvider(email, Provider.google.name());
            return userConverter.toUserDetailDTO(user);
        }else {
            throw new UserNotFoundException(404, "User not found data");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String editUserProfile(MultipartFile file,
                                  String firstName,
                                  String lastName,
                                  String fullName,
                                  String userName,
                                  String phone,
                                  String address,
                                  Principal connectedUser) {
        String email = connectedUser.getName();
        validPhoneNUmber(phone);
        UserEntity user = userRepository.findByEmailAndProvider(email, Provider.local.name());
        if (user != null){
                String avatar = uploadUserImages(file).getWebViewLink();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setFullName(fullName);
                user.setUsername(userName);
                user.setAvatarUrl(avatar);
                user.setPhone(phone);
                user.setAddress(address);
                this.userRepository.save(user);
        }else {
            throw new UserNotFoundException(404, "User not found data");
        }
        return "Update your profile is successfully !";
    }

    public void validPhoneNUmber(String phone){

        Pattern pattern = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()){
            throw new InputPhoneException(501, "Input un correct phone number !");
        }
    }
}
