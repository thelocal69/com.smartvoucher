package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.BlockUserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChangePasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDetailDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.OAuth2UserDetailCustom;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface IUserService {
    String uploadUserImages(MultipartFile fileName, Principal connectedUser);
    List<UserDTO> getAllUser();
    ResponseOutput getAllUser(int page, int limit, String sortBy, String sortField);
    List<UserDTO> searchUserByEmail(String email);
    UserDTO getEmail(UserDTO userDTO);
    UserDetailDTO getUserById(Long id);
    String changePassword(ChangePasswordDTO changePasswordDTO, Principal connectedUser);

    UserDetailDTO getInformationLoginUser(Principal connectedUser);
    UserDetailDTO getInformationOauth2User(OAuth2UserDetailCustom oAuth2UserDetail);
    String editUserProfile(UserDetailDTO userDetailDTO, Principal connectedUser);
    Boolean blockUser(BlockUserDTO blockUserDTO);
}
