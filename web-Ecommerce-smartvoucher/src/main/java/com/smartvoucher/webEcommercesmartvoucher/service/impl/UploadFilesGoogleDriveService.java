package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.service.IUploadFileService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadGoogleDriveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFilesGoogleDriveService implements IUploadFileService {

    private final UploadGoogleDriveUtil uploadGoogleDriveUtil;

    @Autowired
    public UploadFilesGoogleDriveService(final UploadGoogleDriveUtil uploadGoogleDriveUtil) {
        this.uploadGoogleDriveUtil = uploadGoogleDriveUtil;
    }

    @Override
    public List<File> getAllGoogleDriveFiles() {
        return uploadGoogleDriveUtil.getAllGoogleDriveFiles();
    }

    @Override
    public String createdNewFolders(String folderName) {
        return uploadGoogleDriveUtil.createdNewFolders(folderName);
    }

    @Override
    public void deleteFiles(String fileId) {
        this.uploadGoogleDriveUtil.deleteFiles(fileId);
    }
}
