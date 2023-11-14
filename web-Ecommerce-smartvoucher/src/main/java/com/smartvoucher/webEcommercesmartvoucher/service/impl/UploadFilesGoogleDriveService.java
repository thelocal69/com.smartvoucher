package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.google.api.services.drive.model.File;
import com.smartvoucher.webEcommercesmartvoucher.service.IUploadFileService;
import com.smartvoucher.webEcommercesmartvoucher.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFilesGoogleDriveService implements IUploadFileService {

    private final UploadUtil uploadUtil;

    @Autowired
    public UploadFilesGoogleDriveService(final UploadUtil uploadUtil) {
        this.uploadUtil = uploadUtil;
    }

    @Override
    public List<File> getAllGoogleDriveFiles() {
        return uploadUtil.getAllGoogleDriveFiles();
    }

    @Override
    public String createdNewFolders(String folderName) {
        return uploadUtil.createdNewFolders(folderName);
    }

    @Override
    public void deleteFiles(String fileId) {
        this.uploadUtil.deleteFiles(fileId);
    }
}
