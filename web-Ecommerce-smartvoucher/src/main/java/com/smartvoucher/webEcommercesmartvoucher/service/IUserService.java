package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChangePasswordDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDetailDTO;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface IUserService {
    File uploadUserImages(MultipartFile fileName);
    List<UserDTO> getAllUser();
    UserDTO getEmail(UserDTO userDTO);
    UserDetailDTO getUserById(Long id);
    String changePassword(ChangePasswordDTO changePasswordDTO, Principal connectedUser);

    UserDetailDTO getInformationLoginUser(Principal connectedUser);
}
