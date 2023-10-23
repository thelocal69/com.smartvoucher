package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.controller.FileUploadController;
import com.smartvoucher.webEcommercesmartvoucher.exception.InputOutputException;
import com.smartvoucher.webEcommercesmartvoucher.service.IStorageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService {

    private final Path storagePath = Paths.get("uploads");

    public ImageStorageService(){
        try {
            Files.createDirectories(storagePath);
        }catch (IOException ioException){
            throw new InputOutputException(501 ,"Cannot initialize storage", ioException);
        }
    }

    public Boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList("jpg", "png", "jpeg", "bmp").contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file){
        try {
            if (file.isEmpty()){
                throw new InputOutputException(501, "Failed to store empty file", null);
            } else if (!isImageFile(file)) {
                throw new InputOutputException(501, "You can only upload image file", null);
            }
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new InputOutputException(501, "File must be <= 5Mb", null);
            }
            //get filename original
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            //random name, always rename image file because they can override existed file image before !
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storagePath.resolve(
                    Paths.get(generatedFileName)
            ).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storagePath.toAbsolutePath())){
                throw new InputOutputException(501, "Cannot store file outside current directory", null);
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        }catch (IOException ioException){
            throw  new InputOutputException(501, "Failed to store file", ioException);
        }
    }

    @Override
    public Stream<Path> loadAll(){
        //list all files in storageFolder
        try {
            return Files.walk(this.storagePath, 1)
                    .filter(path -> !path.equals(this.storagePath) && !path.toString().contains(".-"))
                    .map(this.storagePath::relativize);
        }catch (IOException ex){
            throw new InputOutputException(501, "Failed to load stored files", ex);
        }
    }

    @Override
    public List<String> getAllUrl() {
        return loadAll()
                .map(path -> {
                    //convert fileName to url(send request "readDetailFile")
                    return MvcUriComponentsBuilder.fromMethodName(FileUploadController.class
                            ,"readDetailFile", path.getFileName().toString()).build().toUri().toString();
                }).collect(Collectors.toList());
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storagePath.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return StreamUtils.copyToByteArray(resource.getInputStream());
            }
        }catch (IOException ioException){
            throw new InputOutputException(501, "Could not read file: " +fileName, ioException);
        }
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {
        try {
            File file = this.storagePath.toFile();
            FileUtils.cleanDirectory(file);
        }catch (IOException ioException){
            throw new InputOutputException(501, "Cannot clean directory !", null);
        }
    }
}
