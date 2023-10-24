package com.smartvoucher.webEcommercesmartvoucher.service;

import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUploadFileService {
    List<File> getAllGoogleDriveFiles();
    String createdNewFolders(String folderName);
    File uploadGoogleDriveFiles(MultipartFile fileName, String folderId);
    void deleteFiles(String fileId);
}
