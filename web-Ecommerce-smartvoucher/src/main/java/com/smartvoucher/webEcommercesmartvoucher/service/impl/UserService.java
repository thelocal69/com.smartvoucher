package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.exception.UserAlreadyExistException;
import com.smartvoucher.webEcommercesmartvoucher.exception.UserNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IUserService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final UploadUtil uploadUtil;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final UserConverter userConverter,
                       final UploadUtil uploadUtil) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.uploadUtil = uploadUtil;
    }

    @Override
    public File uploadUserImages(MultipartFile fileName) {
        String folderId = "1jNZ5_XieGUGKYIhNq3P2tflcKYHCwdb5";
        return uploadUtil.uploadImages(fileName, folderId);
    }

    @Override
    public List<UserDTO> getAllUser() {
        if (userRepository.findAll().isEmpty()){
            throw new ObjectNotFoundException(404, "List user is empty !");
        }
        return userConverter.toUserDTOList(userRepository.findAll());
    }

    @Override
    public UserDTO getEmail(UserDTO userDTO) {
        if (userRepository.findOneByEmail(userDTO.getEmail()) == null){
            throw new UserNotFoundException(404, "User not found or not exist !");
        }
        return userConverter.toUserDTO(userRepository.findOneByEmail(userDTO.getEmail()));
    }
}
