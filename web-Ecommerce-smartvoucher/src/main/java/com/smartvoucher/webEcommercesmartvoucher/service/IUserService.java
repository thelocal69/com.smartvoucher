package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.dto.UserDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    File uploadUserImages(MultipartFile fileName);
}
