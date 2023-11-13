package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    File uploadUserImages(MultipartFile fileName);
    List<UserDTO> getAllUser();
    UserDTO getEmail(UserDTO userDTO);
}
