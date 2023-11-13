package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.converter.UserConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.DuplicationCodeException;
import com.smartvoucher.webEcommercesmartvoucher.exception.InputOutputException;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IUserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final Drive googleDrive;

    public UserService(UserRepository userRepository,
                       UserConverter userConverter,
                       final Drive googleDrive) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.googleDrive = googleDrive;
    }

    public ResponseObject getAllUser() {
        List<UserEntity> list = userRepository.findAll();
        List<UserDTO> listUser = new ArrayList<>();
        if(!list.isEmpty()) {
            for(UserEntity data : list) {
                listUser.add(userConverter.toUserDTO(data));
            }
            return new ResponseObject(200, "List User", listUser);
        } else {
            throw new ObjectNotFoundException(404, "List User is empty");
        }
    }

    public Boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList("jpg", "png", "jpeg", "bmp").contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public File uploadUserImages(MultipartFile fileName) {
        try {
            if (fileName.isEmpty()){
                throw new InputOutputException(501, "Failed to store empty file", null);
            } else if (!isImageFile(fileName)) {
                throw new InputOutputException(500, "You can only upload image file", null);
            }
            float fileSizeInMegabytes = fileName.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new InputOutputException(501, "File must be <= 5Mb", null);
            }
            File fileMetaData = new File();
            String folderId = "1jNZ5_XieGUGKYIhNq3P2tflcKYHCwdb5";
            fileMetaData.setParents(Collections.singletonList(folderId));
            fileMetaData.setName(fileName.getOriginalFilename());
            return googleDrive.files().create(fileMetaData, new InputStreamContent(
                    fileName.getContentType(),
                    new ByteArrayInputStream(fileName.getBytes())
            )).setFields("id, webViewLink").execute();
        }catch (IOException ioException) {
            throw new InputOutputException(501, "Failed to store file", ioException);
        }
    }





}
