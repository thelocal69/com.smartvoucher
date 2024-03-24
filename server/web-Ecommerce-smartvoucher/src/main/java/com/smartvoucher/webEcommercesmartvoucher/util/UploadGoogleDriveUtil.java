package com.smartvoucher.webEcommercesmartvoucher.util;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.smartvoucher.webEcommercesmartvoucher.exception.InputOutputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class UploadGoogleDriveUtil {
    private final Drive googleDrive;

    @Autowired
    public UploadGoogleDriveUtil(final Drive googleDrive) {
        this.googleDrive = googleDrive;
    }

    public List<File> getAllGoogleDriveFiles() {
        try {
            FileList result = googleDrive.files().list()
                    .setFields("nextPageToken, files(id, name, parents, mimeType, webViewLink)")
                    .execute();
            return result.getFiles();
        }catch (IOException ioException){
            log.info("Cannot get all files from google drive !", ioException);
            throw new InputOutputException(501, "Cannot get all files from google drive !", ioException);
        }
    }

    public String createdNewFolders(String folderName) {
        try {
            File fileMetaData = new File();
            fileMetaData.setMimeType("application/vnd.google-apps.folder");
            fileMetaData.setName(folderName);
            File file = googleDrive.files().create(fileMetaData).setFields("id").execute();
            return file.getId();
        } catch (IOException ioException) {
            log.info("Cannot created folder from google drive", ioException);
            throw new InputOutputException(501, "Cannot created folder from google drive", ioException);
        }
    }

    public Boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList("jpg", "png", "jpeg", "bmp").contains(fileExtension.trim().toLowerCase());
    }

    public File uploadImages(MultipartFile fileName, String folderId) {
        try {
            if (fileName.isEmpty()){
                log.info("Failed to store empty file");
                throw new InputOutputException(501, "Failed to store empty file", null);
            } else if (!isImageFile(fileName)) {
                log.info("You can only upload image file");
                throw new InputOutputException(500, "You can only upload image file", null);
            }
            float fileSizeInMegabytes = fileName.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                log.info("File must be <= 5Mb");
                throw new InputOutputException(501, "File must be <= 5Mb", null);
            }
            File fileMetaData = new File();
            fileMetaData.setParents(Collections.singletonList(folderId));
            fileMetaData.setName(fileName.getOriginalFilename());
            return googleDrive.files().create(fileMetaData, new InputStreamContent(
                    fileName.getContentType(),
                    new ByteArrayInputStream(fileName.getBytes())
            )).setFields("id").execute();
        }catch (IOException ioException) {
            log.info("Failed to store file");
            throw new InputOutputException(501, "Failed to store file", ioException);
        }
    }

    public void deleteFiles(String fileId) {
        try {
            this.googleDrive.files().delete(fileId).execute();
        }catch (IOException ioException){
            log.info("Cannot deleted files id = "+fileId,ioException);
            throw new InputOutputException(501, "Cannot deleted files id = "+fileId,ioException);
        }
    }
}
