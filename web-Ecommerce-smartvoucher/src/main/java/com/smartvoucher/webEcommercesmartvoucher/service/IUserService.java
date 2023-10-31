package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    File uploadUserImages(MultipartFile fileName);
}
