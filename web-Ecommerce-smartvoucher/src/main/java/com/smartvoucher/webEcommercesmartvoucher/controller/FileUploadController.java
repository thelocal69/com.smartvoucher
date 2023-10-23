package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("fileUpload")
public class FileUploadController {

    private final IStorageService storageService;

    @Autowired
    public FileUploadController(final IStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/api/uploadImage")
    //Inject Storage Service here
    public ResponseEntity<ResponseObject> uploadImage(@RequestParam("file")MultipartFile file){

            //save files to a folder => use a service
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Upload image is completed",
                            //ex: 06a290064eb94a02a58bfeef36002483.png
                            generatedFileName
                            )
            );
    }
    //How to load all uploaded files ?
    @GetMapping("api/files/{fileName:.+}")
    // /files/06a290064eb94a02a58bfeef36002483.png
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
    //get image's url
    @GetMapping("/api/url")
    public ResponseEntity<ResponseObject> getUploadFiles(){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Find all list urls is completed !",
                            storageService.getAllUrl()
                    )
            );
    }
    @DeleteMapping("/api/")
    public ResponseEntity<ResponseObject> deleteAllFiles() {
        this.storageService.deleteAllFiles();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Clean file in root folder is complete !",
                        ""
                )
        );
    }
}
